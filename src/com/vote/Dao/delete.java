package com.vote.Dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


@Component("delete")
public class delete {
    
	public int deleteCreatvoting(Integer voteid,Session session) {  //删除vote_creatvoting表voteid为id的创建投票纪录
		// TODO Auto-generated method stub	    
	    String sql = "delete CreatUser  where  vote_id=? ";  
		Query query = session.createQuery(sql).setParameter(0, voteid);
		int num = query.executeUpdate();
			 return num;
	}
	
	public int deleteoption(Integer voteid,Session session) {   //删除vote_option表voteid为id的选项纪录		    
	    String sql = "delete option  where  vote_id=? ";  
		Query query = session.createQuery(sql).setParameter(0, voteid);
		 int x=query.executeUpdate();
			 return x;
	}
	
	public int deletevote(Integer voteid,Session session) {     //删除voteid在vote_vote表的所有纪录 
		//删除vote_vote表voteid所有用户投票纪录
		// TODO Auto-generated method stub	    
	    String sql = "delete vote  where  vote_id=? ";  
	    Query query=session.createQuery(sql).setParameter(0, voteid);
	    int x=query.executeUpdate();
	    return x;
	}
	
	public int deleteSEvote(Integer voteid,Integer optionid,String userid,Session session) {      //单选删除  
		//单选删除 区别在于多选直接删除或者添加但是单选没有纪录添加之后要删除之前选的纪录,而多选不用
		//删除vote_vote表voteid为userid的用户选项为optionid投票纪录
		// TODO Auto-generated method stub   
	    String sql = "delete vote where  vote_id=? and option_id !=? and user_id=? ";  //删除语句
		Query query = session.createQuery(sql).setParameter(0, voteid).setParameter(1, optionid).setParameter(2, userid);
		int num = query.executeUpdate();
			 return num;
	}
	
	public int deleteMcvote(Integer voteid,Integer optionid,String userid,Session session) {     //多选删除
		//删除vote_vote表voteid为userid的用户选项为optionid投票纪录
		// TODO Auto-generated method stub
	    String sql = "delete vote  where  vote_id=? and option_id=? and user_id=? ";  //删除语句
		Query query = session.createQuery(sql).setParameter(0, voteid).setParameter(1, optionid).setParameter(2, userid);
		int num = query.executeUpdate();
			 return num;
	}
}
