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
	
	public List<JSONObject> deletevote(Integer voteid,String username,Session session) {  //ɾ������ͶƱ
		// TODO Auto-generated method stub
		delete.deleteCreatvoting(voteid, session);
		delete.deleteoption(voteid, session);
		delete.deletevote(voteid, session);
		List<JSONObject> list=this.executtriggerevent(username, "my", voteid,session);   //���ز�ѯ���û�����ͶƱ,����my��ť
		return list;
	}   
	
	public void voteSave(vote vote,Session session,String chtyoope){   //�û�ͶƱ
		if(chtyoope.equals("0")){
			this.radiovote(vote, session);
		}else{
			this.checkboxvote(vote, session);
		}
	}
	
	public void radiovote(vote vote ,Session session){    //��ѡ���
	    boolean flag=executed.votexecuted(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session) ; //�ж��û��Ƿ�Ͷ�����ѡ��
	    if(flag ==true){
	    delete.deleteMcvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session); //�ǵĻ�ֱ��ɾ��
	    }else
	    {
	    	delete.deleteSEvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session);
	    	//���ǵĻ��Ȱ����ͶƱ���û�Ͷ������ѡ���ͶƱ��¼ɾ��,����ӱ��μ�¼,��Ϊ���ǵ�ѡ,���ܳ����û�ͬʱͶ������ѡ��
	    	session.save(vote);
	    }
	}
	
	
	public void checkboxvote(vote vote,Session session){  //��ѡ���    
	    boolean flag=executed.votexecuted(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session);  //�жϸ��û��Ƿ��Ѿ�Ͷ��Ʊ��
	    if(flag ==true){    	 
	    	int num=delete.deleteMcvote(vote.getVote_id(), vote.getOption_id(), vote.getUser_id(), session) ; //����Ѿ�ͶƱ�˾�ֱ��ɾ��
	    }else{
	    	session.save(vote); //û��ͶƱ����Ӽ�¼
	    }
	}
	
	public List<JSONObject> addList(CreatUser creatUser,List<option> options,User user,Session session){  //����ͶƱ�ɹ��󷵻ص�ͶƱ�б�
		add.CreatUserSave(creatUser, session);
		for(option r:options){
			add.optionSave(r, session);
		}
		add.save(user, session);
		List<JSONObject> list=this.executtriggerevent(user.getUserid(), "my", 0, session);  
		//voteidΪ0����˼�� ��Ϊ����my��ť����Ҫvoteid,ֻ��Ҫ�û���
		return list;
	}
	
	public List<JSONObject> addvote(vote vote,String choosetpye,Session session){    //�û�ͶƱ�󷵻����ͶƱ����������
		  this.voteSave(vote, session, choosetpye);
		  List<JSONObject> list=this.executtriggerevent(vote.getUser_id(),"view",vote.getVote_id(),session);
		  return list;
	}
	
	public List<JSONObject> add(CreatUser creatUser,List<option> options,User user,String triggerevent,vote vote,String choosetype,Session session) {        //����ͶƱ�ķ���   
		// TODO Auto-generated method stub
		if(triggerevent.equals("creat")){    //�ж��Ƿ��Ǵ���ͶƱ
		List<JSONObject> list=this.addList(creatUser, options, user,session);
		return list;
	  }else if(triggerevent.equals("vote")){   //�Ƿ����û�ͶƱ
		  List<JSONObject> list=this.addvote(vote, choosetype,session);
		  return list;
	  }else{        //���򷵻ؿ�
		  return null;
	  }
	}
		
	 public ArrayList<ArrayList>  mylist(String username,Session session){     //���username���û���
	    	//������ѯ�ҵİ�ť����,���ش����ı��⣬���,�ͷ���ͶƱidһ�������û�����һ����ͶƱ����   	
		    ArrayList<ArrayList> list3=new ArrayList(); //�����list2ѭ����֮��Ͱ�list2���
		    List<CreatUser> list=executed.AllCreatUser(username, session)  ;
		    //���û����Ž�ȥ��ѯ����û������д����ı���voteid�ó���
		    for(CreatUser r:list)
		    {
			    ArrayList list2=new ArrayList(); //��������в�ѯ�����Ķ����������������
		    	list2.add(r.getVote_id());      //��ô����������Ψһid
		    	list2.add(r.getVotetitle());   //
		    	list2.add(r.getVotecontent());
		        int b=executed.myOptions(r.getVote_id(), session);  //��ѯ��my��ť��ͶƱ����
		        list2.add(b);
		        list3.add(list2);
		    }			    
		  return list3;
	    }
	 
	 public ArrayList<ArrayList> Viewlist(Integer voteid,Session session){   //�鿴����ͶƱ����ϸ����,���ز鿴��ť������
	    	List<CreatUser> list =executed.CreatUser(voteid, session);  //��ѯΨһ�Ĵ���ͶƱ
	    	ArrayList<ArrayList> list4=new ArrayList(); //�����list2ѭ����֮��Ͱ�list2���
	        for(CreatUser r : list){
	        	 ArrayList list2=new ArrayList(); //��������в�ѯ�����Ķ����������������
	        	 list2.add(r.getVote_id());      //��ô����������Ψһid
	 	    	 list2.add(r.getVotetitle());   //
	 	    	 list2.add(r.getVotecontent());
	 	    	 List<option> list3=executed.option(voteid, session);   //���ѡ���ΨһId ��ѡ������
	 	    	 ArrayList list6=new ArrayList();   //�����������ѡ���ID������,��ѡ���ͶƱ������������
	 	    	 for(option r2:list3){
	 			    ArrayList list5=new ArrayList();   //�����洢���ص�ѡ�����ֺ�ѡ��id
	 		    	list5.add(r2.getOption_id());   //ÿ��ѡ�������id ,�������ݸ�vote_vote���ѯÿ��ѡ��������
	 		    	String optionname=r2.getOption_name();
	 		    	int d=executed.optionQuantity(voteid, r2.getOption_id(), session);
	 		    	optionkey =new optionkey(r2.getOption_name(),d);  //������ѡ�����ֺ�ֵ��װ
	 		    	list5.add(optionkey);
	 		    	list6.add(list5);  //���ÿ��ѭ�����ѡ�����ֺ�ѡ������
	 		    }
	 		    list2.add(list6);
	 		    list2.add(r.getEndvoteDate());
	 		    list2.add(r.getVotepalace());
	 		    list2.add(r.getChoosetype());
	 		    list4.add(list2);
	        }
	        return list4;
	    } 
	 
	 public List<JSONObject> executtriggerevent(String username,String triggerevent,Integer voteid,Session session) {   //������ѯ�¼�
			// TODO Auto-generated method stub
			if(triggerevent.equals("my")){
				ArrayList<ArrayList> list=this.mylist(username, session);    //�õ���ѯ���ص�����
				JSONObject list2=new JSONObject();                 //�����������ת����JSON����
				JSONArray list7=new JSONArray();                    //��ÿһ��JSON������ӵ�JSON����,��Ȼ�ظ��ı��������һ�����ǰ��ȫ����
				List<JSONObject> list4=new ArrayList<JSONObject>() ;   //��JSON������ӵ�����
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
			}else if(triggerevent.equals("view")){    //��Ϊ��Ψһ��id,���Է��ص�ֻ��һ�����鲻��ѭ��Ҳ����
				ArrayList<ArrayList> list=this.Viewlist(voteid,session);   //��ò�ѯ����
				ArrayList list2=list.get(0);
				JSONObject list3=new JSONObject();     //������ת����JSON����
				List<JSONObject> list4=new ArrayList<JSONObject>() ;    //��JSON������ӵ����鲢����
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
