package com.vote.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import com.vote.eity.CreatUser;
import com.vote.eity.User;
import com.vote.eity.ban;
import com.vote.eity.option;
import com.vote.eity.vote;

@Component("executed")
public class executed {
	  public boolean ban_ip(String userid ,Session session ){   //判断vote_banip里面是否有被传进去的用户名
	    	String sql2 = " from ban  where  userid=? ";   
	    	Query query = session.createQuery(sql2).setParameter(0, userid);
		    List<ban> list = query.list();
		    return  !list.isEmpty();
	    }
	  
	  public boolean User(String userid,Session session){   //判断用户明是否存在,是否已经在用户表存在 
	    	String sql2 = " from User  where  userid=? ";   
	    	//判断用户表是否有这个用户  操作的不是数据库的表vote_user,是持久化对象类user_User
		    //userid是创建投票表里面的数据库用户名字段  ,不是持久化对象的变量
		    // select username 后面不能加where 不然会出错
	    	Query query = session.createQuery(sql2).setParameter(0, userid);
		    List<User> list = query.list();;
		    return  !list.isEmpty();
	    }
	  
	  public boolean  votexecuted(Integer voteid,Integer optionid,String userid,Session session){   //判断用户是否已经投过票啦
	    	String sql2 = " from vote where  vote_id=? and user_id=? and option_id=? ";   //查询纪录是否存在
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
	    	Query query = session.createQuery(sql2).setParameter(0, voteid).setParameter(1, userid).setParameter(2, optionid);
		    List<vote> list = query.list();
		    return !list.isEmpty();
	    }
	  
	  public List<option> option(int voteid,Session session){    //返回是选项名称和id  只有两个元素,一个是选项名字，另一个是选项id
	    	String sql = " from option  where  vote_id=? ";   //首先查询用户的创建表
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
		    Query query = session.createQuery(sql).setParameter(0, voteid);
		    List<option> list = query.list();
		    return list;
	    }
	  
	  public List<CreatUser>  CreatUser(Integer voteid,Session session){  //返回用户创建的一个投票的所有东西
	    	String sql = " from CreatUser  where  vote_id=? ";   //首先查询用户的创建表
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
		    Query query = session.createQuery(sql).setParameter(0, voteid);
		    List<CreatUser> list = query.list();
		    return list;
	    }
	  
	  public List<CreatUser> AllCreatUser(String userid,Session session){  //返回用户创建所有投票的所有东西
	    	String sql = " from CreatUser  where  userid=? ";   //首先查询用户的创建表
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
		    Query query = session.createQuery(sql).setParameter(0, userid);
		    List<CreatUser> list = query.list();
		    return list;
	    }
	  
	  public int optionQuantity(int voteid,int optionid,Session session){   //返回每个voteid的投票表的每个选项的总人数 
	    	String sql2 = " from vote  where  vote_id=?  and option_id=? ";   //统计用户创建的投票表的投票人数
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
	    	Query query = session.createQuery(sql2).setParameter(0, voteid).setParameter(1, optionid);
		    List<vote> list = query.list();
		    int i=list.size();
		    return i;
	    }
	  
	  public int  Maxvoteid(String username,Session session){    //查询该用户创建的投票的最大voteid,即刚创建的投票
	    	String sql = " from CreatUser  where  vote_id=(select max(vote_id) from CreatUser) ";   //首先查询用户的创建表
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
		    Query query = session.createQuery(sql);
		    int i=0;
		    List<CreatUser> list = query.list();    
		    i=list.get(0).getVote_id();		    
		    return i+1;   //+1是因为Hibernate不会一个一个创建,会一整次创建,所以不加一的话就会查找到当前用户已经创建投票的最大值voteid,而不是刚创建的投票,从而报错
	    }
	  
	  public int myOptions(int voteId,Session session) {    //返回my按钮的人数
	    	String sql2 = " from vote  where  vote_id=? ";   //统计用户创建的投票表的投票人数
		    //userid是创建投票表里面的数据库用户名字段  ,不是username
		    // select username 后面不能加where 不然会出错
	    	Query query = session.createQuery(sql2);
		    query.setParameter(0, voteId);
		    List<vote> list3 = query.list();
		    Set<vote> set = new HashSet<vote>();
		    for(vote e : list3){
		    	set.add(e);
		    }
		    int i=set.size();
		    return i;
	    }
}
