package com.vote.Dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import com.vote.eity.CreatUser;
import com.vote.eity.User;
import com.vote.eity.ban;
import com.vote.eity.option;
import com.vote.eity.vote;

@DependsOn("executed")
@Component("add")
public class add {
	
	@Autowired
	private executed executed;
	  
	public void save(User user,Session session){   //添加用户
		Boolean flag=executed.User(user.getUserid(), session);
		if(flag == false){
		session.save(user);
		}
	}
	
	public void BanSave(ban ban,Session session){   //违纪被封的用户
		Boolean flag=executed.ban_ip(ban.getUserid(), session);
		if(flag == false){
		session.save(ban);
		}
	}
	
	public void CreatUserSave(CreatUser creatuser,Session session){   //用户创建的投票
		session.save(creatuser);
	}
	
	public void optionSave(option option,Session session){   //用户创建投票的选项
		session.save(option);
	}
	
	/*public void voteSave(vote vote,Session session){   //用户投票
		session.save(vote);
	}*/
}
