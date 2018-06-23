package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("User")
public class User {
	public User(String userid, String userentry) {
		super();
		this.userid = userid;
		Userentry = userentry;
	}
	private Integer id;
	private String userid;     //用户id
	private String Userentry;   //用户入口
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserentry() {
		return Userentry;
	}
	public void setUserentry(String userentry) {
		Userentry = userentry;
	}
	
	public User(){}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
