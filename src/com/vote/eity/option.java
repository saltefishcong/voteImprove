package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("option")
public class option {
	public option(Integer vote_id, String option_name) {
		super();
		this.vote_id = vote_id;
		this.option_name = option_name;
	}
	private Integer option_id;  //Ϊÿ��ѡ���һ��������id
    private Integer vote_id;    //����ͶƱ��id,����option_idʶ��ͬ����ѡ��
    private String option_name; //ѡ������
	public Integer getOption_id() {
		return option_id;
	}
	public void setOption_id(Integer option_id) {
		this.option_id = option_id;
	}
	public Integer getVote_id() {
		return vote_id;
	}
	public void setVote_id(Integer vote_id) {
		this.vote_id = vote_id;
	}
	public String getOption_name() {
		return option_name;
	}
	public void setOption_name(String option_name) {
		this.option_name = option_name;
	}
	
	public option(){}
}
