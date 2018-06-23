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
	  public boolean ban_ip(String userid ,Session session ){   //�ж�vote_banip�����Ƿ��б�����ȥ���û���
	    	String sql2 = " from ban  where  userid=? ";   
	    	Query query = session.createQuery(sql2).setParameter(0, userid);
		    List<ban> list = query.list();
		    return  !list.isEmpty();
	    }
	  
	  public boolean User(String userid,Session session){   //�ж��û����Ƿ����,�Ƿ��Ѿ����û������ 
	    	String sql2 = " from User  where  userid=? ";   
	    	//�ж��û����Ƿ�������û�  �����Ĳ������ݿ�ı�vote_user,�ǳ־û�������user_User
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,���ǳ־û�����ı���
		    // select username ���治�ܼ�where ��Ȼ�����
	    	Query query = session.createQuery(sql2).setParameter(0, userid);
		    List<User> list = query.list();;
		    return  !list.isEmpty();
	    }
	  
	  public boolean  votexecuted(Integer voteid,Integer optionid,String userid,Session session){   //�ж��û��Ƿ��Ѿ�Ͷ��Ʊ��
	    	String sql2 = " from vote where  vote_id=? and user_id=? and option_id=? ";   //��ѯ��¼�Ƿ����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
	    	Query query = session.createQuery(sql2).setParameter(0, voteid).setParameter(1, userid).setParameter(2, optionid);
		    List<vote> list = query.list();
		    return !list.isEmpty();
	    }
	  
	  public List<option> option(int voteid,Session session){    //������ѡ�����ƺ�id  ֻ������Ԫ��,һ����ѡ�����֣���һ����ѡ��id
	    	String sql = " from option  where  vote_id=? ";   //���Ȳ�ѯ�û��Ĵ�����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
		    Query query = session.createQuery(sql).setParameter(0, voteid);
		    List<option> list = query.list();
		    return list;
	    }
	  
	  public List<CreatUser>  CreatUser(Integer voteid,Session session){  //�����û�������һ��ͶƱ�����ж���
	    	String sql = " from CreatUser  where  vote_id=? ";   //���Ȳ�ѯ�û��Ĵ�����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
		    Query query = session.createQuery(sql).setParameter(0, voteid);
		    List<CreatUser> list = query.list();
		    return list;
	    }
	  
	  public List<CreatUser> AllCreatUser(String userid,Session session){  //�����û���������ͶƱ�����ж���
	    	String sql = " from CreatUser  where  userid=? ";   //���Ȳ�ѯ�û��Ĵ�����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
		    Query query = session.createQuery(sql).setParameter(0, userid);
		    List<CreatUser> list = query.list();
		    return list;
	    }
	  
	  public int optionQuantity(int voteid,int optionid,Session session){   //����ÿ��voteid��ͶƱ���ÿ��ѡ��������� 
	    	String sql2 = " from vote  where  vote_id=?  and option_id=? ";   //ͳ���û�������ͶƱ���ͶƱ����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
	    	Query query = session.createQuery(sql2).setParameter(0, voteid).setParameter(1, optionid);
		    List<vote> list = query.list();
		    int i=list.size();
		    return i;
	    }
	  
	  public int  Maxvoteid(String username,Session session){    //��ѯ���û�������ͶƱ�����voteid,���մ�����ͶƱ
	    	String sql = " from CreatUser  where  vote_id=(select max(vote_id) from CreatUser) ";   //���Ȳ�ѯ�û��Ĵ�����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
		    Query query = session.createQuery(sql);
		    int i=0;
		    List<CreatUser> list = query.list();    
		    i=list.get(0).getVote_id();		    
		    return i+1;   //+1����ΪHibernate����һ��һ������,��һ���δ���,���Բ���һ�Ļ��ͻ���ҵ���ǰ�û��Ѿ�����ͶƱ�����ֵvoteid,�����Ǹմ�����ͶƱ,�Ӷ�����
	    }
	  
	  public int myOptions(int voteId,Session session) {    //����my��ť������
	    	String sql2 = " from vote  where  vote_id=? ";   //ͳ���û�������ͶƱ���ͶƱ����
		    //userid�Ǵ���ͶƱ����������ݿ��û����ֶ�  ,����username
		    // select username ���治�ܼ�where ��Ȼ�����
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
