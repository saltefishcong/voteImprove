package com.vote.Dao;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import com.vote.eity.CreatUser;
import com.vote.eity.User;
import com.vote.eity.UserFactory;
import com.vote.eity.ban;
import com.vote.eity.option;
import com.vote.eity.vote;

import net.sf.json.JSONObject;

@DependsOn({"delete"})
@Repository("UserDao")
public class UserDao {
	
	
	
}
