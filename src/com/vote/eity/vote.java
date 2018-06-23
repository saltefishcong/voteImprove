package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("vote")
public class vote {
	public vote(String user_id, Integer vote_id, Integer option_id, Long lasttime, String iP, String votepalace) {
		super();
		this.user_id = user_id;
		this.vote_id = vote_id;
		this.option_id = option_id;
		this.lasttime = lasttime;
		IP = iP;
		this.votepalace = votepalace;
	}
	private Integer id;             //给用户每次唯一的id,自增
	private String user_id;     //用户名
	   private Integer vote_id; //创建投票id
	   private Integer option_id; //选项表唯一id，自增
	   private Long lasttime;   //用户最后一次投票时间
	   private String IP;      //用户id
	   private String votepalace; //用户投票地点
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getVote_id() {
		return vote_id;
	}
	public void setVote_id(Integer vote_id) {
		this.vote_id = vote_id;
	}
	public Integer getOption_id() {
		return option_id;
	}
	public void setOption_id(Integer option_id) {
		this.option_id = option_id;
	}
	public Long getLasttime() {
		return lasttime;
	}
	public void setLasttime(Long lasttime) {
		this.lasttime = lasttime;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getVotepalace() {
		return votepalace;
	}
	public void setVotepalace(String votepalace) {
		this.votepalace = votepalace;
	}
	
	public  vote() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int hashCode() {   
		return this.user_id.hashCode();
	}
	public boolean equals(Object o) {
		if(!(o instanceof vote)) {
			return false;
		}
		vote target = (vote)o;
		return this.user_id.equals(target.user_id);
	}
}
