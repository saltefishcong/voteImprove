package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ban")
@Scope("prototype")
public class ban {
	 public ban(String userid, String ip) {
		super();
		this.userid = userid;
		this.ip = ip;
	}
	private Integer id;
	private String userid;  //������û���
     private String ip;         //�����ip
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public ban(){}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
