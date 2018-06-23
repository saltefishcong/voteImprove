package com.vote.eity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.vote.Dao.UserSerivice;
import com.vote.Dao.filter;

public class test {
	HttpServletRequest request = ServletActionContext.getRequest();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       ApplicationContext ctx=new ClassPathXmlApplicationContext("bean.xml");
       UserSerivice userSerivice=ctx.getBean("UserService",UserSerivice.class);
      /* option aOption=new option(12,"li");
       option aOption2=new option(12,"lihua");
       List<option> list=new ArrayList<option>();
       list.add(aOption);
       list.add(aOption2);
       userSerivice.addList(new CreatUser("asd","wqe","wew",123L,456L,"dsas","0"), list,new User("asd","da"));*/
      // System.out.println(userSerivice.execu(3, 4, "asd"));
      // userSerivice.addvote(new  vote("sdf",13,7,12L,"sad","asdsa"), "0");
       filter filter=ctx.getBean("filter",filter.class);
      
       //System.out.println(filter.getLocalIp(request));
	}

}
