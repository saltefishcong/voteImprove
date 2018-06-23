package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("CreatUser")
public class CreatUser {
	public CreatUser(String userid, String votetitle, String votecontent, Long creatvoteDate, Long endvoteDate,
			String votepalace, String choosetype) {
		super();
		this.userid = userid;
		Votetitle = votetitle;
		Votecontent = votecontent;
		CreatvoteDate = creatvoteDate;
		EndvoteDate = endvoteDate;
		this.votepalace = votepalace;
		this.choosetype = choosetype;
	}
	private Integer vote_id;      //�������ID,���ݿ�����
	private String userid;       //�û���
	private String Votetitle;    //����
	private String Votecontent;  //����
	private Long CreatvoteDate;  //��ʼ����
	private Long EndvoteDate;    //��������
	private String votepalace;   //ͶƱ�ص�
	private String choosetype;   //��ѡ���Ƕ�ѡ
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getVotetitle() {
		return Votetitle;
	}
	public void setVotetitle(String votetitle) {
		Votetitle = votetitle;
	}
	public String getVotecontent() {
		return Votecontent;
	}
	public void setVotecontent(String votecontent) {
		Votecontent = votecontent;
	}
	public Long getCreatvoteDate() {
		return CreatvoteDate;
	}
	public void setCreatvoteDate(Long creatvoteDate) {
		CreatvoteDate = creatvoteDate;
	}
	public Long getEndvoteDate() {
		return EndvoteDate;
	}
	public void setEndvoteDate(Long endvoteDate) {
		EndvoteDate = endvoteDate;
	}
	public String getVotepalace() {
		return votepalace;
	}
	public void setVotepalace(String votepalace) {
		this.votepalace = votepalace;
	}
	public String getChoosetype() {
		return choosetype;
	}
	public void setChoosetype(String choosetype) {
		this.choosetype = choosetype;
	}
	
	public CreatUser(){}
	public Integer getVote_id() {
		return vote_id;
	}
	public void setVote_id(Integer vote_id) {
		this.vote_id = vote_id;
	}
	
	public String toString() {
		return   " "+userid+" "+Votetitle+" "+Votecontent+" "+CreatvoteDate+" "+EndvoteDate+" "+votepalace+" "+choosetype;
	}
}
