package com.vote.Dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


@Component("delete")
public class delete {
    
	public int deleteCreatvoting(Integer voteid,Session session) {  //ɾ��vote_creatvoting��voteidΪid�Ĵ���ͶƱ��¼
		// TODO Auto-generated method stub	    
	    String sql = "delete CreatUser  where  vote_id=? ";  
		Query query = session.createQuery(sql).setParameter(0, voteid);
		int num = query.executeUpdate();
			 return num;
	}
	
	public int deleteoption(Integer voteid,Session session) {   //ɾ��vote_option��voteidΪid��ѡ���¼		    
	    String sql = "delete option  where  vote_id=? ";  
		Query query = session.createQuery(sql).setParameter(0, voteid);
		 int x=query.executeUpdate();
			 return x;
	}
	
	public int deletevote(Integer voteid,Session session) {     //ɾ��voteid��vote_vote������м�¼ 
		//ɾ��vote_vote��voteid�����û�ͶƱ��¼
		// TODO Auto-generated method stub	    
	    String sql = "delete vote  where  vote_id=? ";  
	    Query query=session.createQuery(sql).setParameter(0, voteid);
	    int x=query.executeUpdate();
	    return x;
	}
	
	public int deleteSEvote(Integer voteid,Integer optionid,String userid,Session session) {      //��ѡɾ��  
		//��ѡɾ�� �������ڶ�ѡֱ��ɾ��������ӵ��ǵ�ѡû�м�¼���֮��Ҫɾ��֮ǰѡ�ļ�¼,����ѡ����
		//ɾ��vote_vote��voteidΪuserid���û�ѡ��ΪoptionidͶƱ��¼
		// TODO Auto-generated method stub   
	    String sql = "delete vote where  vote_id=? and option_id !=? and user_id=? ";  //ɾ�����
		Query query = session.createQuery(sql).setParameter(0, voteid).setParameter(1, optionid).setParameter(2, userid);
		int num = query.executeUpdate();
			 return num;
	}
	
	public int deleteMcvote(Integer voteid,Integer optionid,String userid,Session session) {     //��ѡɾ��
		//ɾ��vote_vote��voteidΪuserid���û�ѡ��ΪoptionidͶƱ��¼
		// TODO Auto-generated method stub
	    String sql = "delete vote  where  vote_id=? and option_id=? and user_id=? ";  //ɾ�����
		Query query = session.createQuery(sql).setParameter(0, voteid).setParameter(1, optionid).setParameter(2, userid);
		int num = query.executeUpdate();
			 return num;
	}
}
