package com.vote.eity;

import org.springframework.stereotype.Component;

import com.vote.eity.User;

public interface UserFactory {
	public  User getUser();
	
	public option getOption();
	
	public ban getBan();
	
	public CreatUser getCreatUser();
	
	public vote getVote();
}
