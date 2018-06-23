package com.vote.Dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import com.vote.eity.CreatUser;
import com.vote.eity.User;
import com.vote.eity.ban;
import com.vote.eity.optionkey;
import com.vote.eity.option;
import com.vote.eity.vote;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@DependsOn({"UserDao","sessionFactory","add","delete","executed"})
@Service("UserService")
public class UserSerivice {
   
	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
   
	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private add add;
	
	@Autowired
	private delete delete;
	
	@Autowired
	private executed executed;
	
	private User user;
	
	private optionkey  optionkey;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
   /* public void regter(vote vote){
    	Session session=sessionFactory.openSession();
    	add.voteSave(vote, session);
		session.close();
	}*/
	
	public List<JSONObject> deletevote(Integer voteid,String username,Session session) {  //删除整个投票
		// TODO Auto-generated method stub
		delete.deleteCreatvoting(voteid, session);
		delete.deleteoption(voteid, session);
		delete.deletevote(voteid, session);
		List<JSONObject> list=this.executtriggerevent(username, "my", voteid,session);   //返回查询该用户所有投票,就是my按钮
		return list;
	}   
	
	public void voteSave(vote vote,Session session,String chtyoope){   //用户投票
		if(chtyoope.equals("0")){
			this.radiovote(vote, session);
		}else{
			this.checkboxvote(vote, session);
		}
	}
	
	public void radiovote(vote vote ,Session session){    //单选添加
	    boolean flag=executed.votexecuted(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session) ; //判断用户是否投过这个选项
	    if(flag ==true){
	    delete.deleteMcvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session); //是的话直接删除
	    }else
	    {
	    	delete.deleteSEvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session);
	    	//不是的话先把这次投票的用户投过其他选项的投票纪录删除,再添加本次纪录,因为这是单选,不能出现用户同时投了两个选项
	    	session.save(vote);
	    }
	}
	
	
	public void checkboxvote(vote vote,Session session){  //多选添加    
	    boolean flag=executed.votexecuted(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session);  //判断该用户是否已经投过票了
	    if(flag ==true){    	 
	    	int num=delete.deleteMcvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session) ; //如果已经投票了就直接删除
	    }else{
	    	session.save(vote); //没有投票就添加纪录
	    }
	}
	
	public List<JSONObject> addList(CreatUser creatUser,List<option> options,User user,Session session){  //创建投票成功后返回的投票列表
		add.CreatUserSave(creatUser, session);
		for(option r:options){
			add.optionSave(r, session);
		}
		add.save(user, session);
		List<JSONObject> list=this.executtriggerevent(user.getUserid(), "my", 0, session);  
		//voteid为0的意思是 因为返回my按钮不需要voteid,只需要用户名
		return list;
	}
	
	public List<JSONObject> addvote(vote vote,String choosetpye,Session session){    //用户投票后返回这个投票的所有数据
		  this.voteSave(vote, session, choosetpye);
		  List<JSONObject> list=this.executtriggerevent(vote.getUser_id(),"view",vote.getVote_id(),session);
		  return list;
	}
	
	public List<JSONObject> add(CreatUser creatUser,List<option> options,User user,String triggerevent,vote vote,String choosetype,Session session) {        //创建投票的方法   
		// TODO Auto-generated method stub
		if(triggerevent.equals("creat")){    //判断是否是创建投票
		List<JSONObject> list=this.addList(creatUser, options, user,session);
		return list;
	  }else if(triggerevent.equals("vote")){   //是否是用户投票
		  List<JSONObject> list=this.addvote(vote, choosetype,session);
		  return list;
	  }else{        //否则返回空
		  return null;
	  }
	}
		
	 public ArrayList<ArrayList>  mylist(String username,Session session){     //这个username是用户名
	    	//用来查询我的按钮请求,返回创建的标题，简介,和返回投票id一样但是用户名不一样的投票人数   	
		    ArrayList<ArrayList> list3=new ArrayList(); //负责把list2循环完之后就把list2添加
		    List<CreatUser> list=executed.AllCreatUser(username, session)  ;
		    //把用户名放进去查询获得用户名所有创建的表并把voteid拿出来
		    for(CreatUser r:list)
		    {
			    ArrayList list2=new ArrayList(); //负责把所有查询出来的东西并接起来并添加
		    	list2.add(r.getVote_id());      //获得创建表的自增唯一id
		    	list2.add(r.getVotetitle());   //
		    	list2.add(r.getVotecontent());
		        int b=executed.myOptions(r.getVote_id(), session);  //查询的my按钮的投票人数
		        list2.add(b);
		        list3.add(list2);
		    }			    
		  return list3;
	    }
	 
	 public ArrayList<ArrayList> Viewlist(Integer voteid,Session session){   //查看创建投票的详细内容,返回查看按钮的内容
	    	List<CreatUser> list =executed.CreatUser(voteid, session);  //查询唯一的创建投票
	    	ArrayList<ArrayList> list4=new ArrayList(); //负责把list2循环完之后就把list2添加
	        for(CreatUser r : list){
	        	 ArrayList list2=new ArrayList(); //负责把所有查询出来的东西并接起来并添加
	        	 list2.add(r.getVote_id());      //获得创建表的自增唯一id
	 	    	 list2.add(r.getVotetitle());   //
	 	    	 list2.add(r.getVotecontent());
	 	    	 List<option> list3=executed.option(voteid, session);   //获得选项的唯一Id 和选项名字
	 	    	 ArrayList list6=new ArrayList();   //用来添加所有选项的ID，名字,和选项的投票人数放在里面
	 	    	 for(option r2:list3){
	 			    ArrayList list5=new ArrayList();   //用来存储返回的选项名字和选项id
	 		    	list5.add(r2.getOption_id());   //每个选项独立的id ,用来传递给vote_vote表查询每个选项总人数
	 		    	String optionname=r2.getOption_name();
	 		    	int d=executed.optionQuantity(voteid, r2.getOption_id(), session);
	 		    	optionkey =new optionkey(r2.getOption_name(),d);  //用来把选项名字和值封装
	 		    	list5.add(optionkey);
	 		    	list6.add(list5);  //添加每次循环完的选项名字和选项人数
	 		    }
	 		    list2.add(list6);
	 		    list2.add(r.getEndvoteDate());
	 		    list2.add(r.getVotepalace());
	 		    list2.add(r.getChoosetype());
	 		    list4.add(list2);
	        }
	        return list4;
	    } 
	 
	 public List<JSONObject> executtriggerevent(String username,String triggerevent,Integer voteid,Session session) {   //触发查询事件
			// TODO Auto-generated method stub
			if(triggerevent.equals("my")){
				ArrayList<ArrayList> list=this.mylist(username, session);    //拿到查询返回的数组
				JSONObject list2=new JSONObject();                 //将数组的数据转换成JSON对象
				JSONArray list7=new JSONArray();                    //把每一个JSON对象添加到JSON数组,不然重复的变量明最后一个会把前面全覆盖
				List<JSONObject> list4=new ArrayList<JSONObject>() ;   //把JSON对象添加到数组
				for(int i=0;i<list.size();i++){
					ArrayList list3=list.get(i);			
					list2.put("voteid", list3.get(0));
					list2.put("Votetitle", list3.get(1));
					list2.put("Votecontent", list3.get(2));
					list2.put("num",list3.get(3));
					list7.add(i, list2);
				}
				list4.addAll(list7);
				return list4;
			}else if(triggerevent.equals("view")){    //因为是唯一的id,所以返回的只是一个数组不用循环也可以
				ArrayList<ArrayList> list=this.Viewlist(voteid,session);   //获得查询数据
				ArrayList list2=list.get(0);
				JSONObject list3=new JSONObject();     //将数据转换成JSON对象
				List<JSONObject> list4=new ArrayList<JSONObject>() ;    //将JSON对象添加到数组并返回
				list3.put("Voteid", list2.get(0));
				list3.put("Votetitle", list2.get(1));
				list3.put("Votecontent", list2.get(2));
				list3.put("option", list2.get(3));			
				list3.put("EndvoteDate", list2.get(4));
				list3.put("Votepalace", list2.get(5));
				list3.put("Choosetype", list2.get(6));
				list4.add(list3);
				return list4;
			}else{
				return null;
			}
			
		}
    
}
