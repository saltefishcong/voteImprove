package com.vote.struts2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import com.vote.Dao.UserSerivice;
import com.vote.eity.CreatUser;
import com.vote.eity.User;
import com.vote.eity.option;
import com.vote.eity.vote;
import com.vote.Dao.executed;
import com.vote.Dao.filter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@DependsOn({"UserService","sessionFactory"})
public class VotingAction {
	private Integer id;               //����ͶƱ��id,���ݿ��Զ�����
	private String username;          //����ͶƱ��QQ��
	private String Votetitle;         //����ͶƱ�ı���
	private String Votecontent;       //����ͶƱ������
	private String VoteOption="";     //����ͶƱ��ѡ��
	private Date  CreatvoteDate;      //����ͶƱ��ʱ��
	private String EndvoteDate;       //����ͶƱ�Ľ�������
	private String place;             //����ͶƱ�ĵص�
	private String choosetype;        //ͶƱ������,��ѡ���Ƕ�ѡ
	private String Userentry;         //�û�ͶƱͨ��
	private JSONObject jsonobject2;
	HttpServletResponse response = ServletActionContext.getResponse();
	
	@Autowired
	private UserSerivice userSerivice;
	
	@Autowired
	private executed  executed;
	
	@Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
	
	@Autowired
	private filter filter;
	
	HttpServletRequest request=ServletActionContext.getRequest();
	String jsonStr=request.getParameter("json");   //��ȡ��json�ַ���
	String IP;
	String position;
	
	public boolean position(Session session) throws Exception{	
		boolean flag =filter.interceHours(CreatvoteDate =new Date());
		if(flag != true){
			return false;
		}		
		jsonobject2 = JSONObject.fromObject(jsonStr);
		Integer voteid=Integer.parseInt(jsonobject2.getString("voteid"));
	    flag=filter.interceENDdate(voteid, session);
		if(flag !=true){
		return false;
		}
		position=jsonobject2.getString("position");
		System.out.println("��������λ��"+position);
		 flag=filter.interceptposition(request, position, voteid, session);
		return flag;
	}
	
	
	public String vote() throws Exception{
		Session session=sessionFactory.openSession();
		jsonobject2 = JSONObject.fromObject(jsonStr);
		String name=jsonobject2.getString("name");		
		if(name.equals("my")){   //�Ƿ�����my��ť 
			return this.executed(name,session);
		}else if(name.equals("view")){    //�Ƿ����˲鿴��ť
			return this.executed(name,session);
		}else if(name.equals("creat")){   //�Ƿ񴴽���ͶƱ
			boolean flag=filter.intercept(jsonStr);
			if(flag==true){
			return this.add(name,session);
			}else{
				return "jsonobject2";
			}
		}else if(name.equals("vote")){    //�Ƿ�ͶƱ				
			boolean flag=this.position(session);
			System.out.println(position);
			if(flag==true){
			return this.addvote(IP, position, name,session);
			}else{
				return "jsonobject2";
			}
		}else if(name.equals("delete")){   //�Ƿ�ɾ��������ͶƱ
			return this.delete(session);
		}else{
			return null;
		}
	   
	}
	
	
	public String delete(Session session){
		jsonobject2 = JSONObject.fromObject(jsonStr);
		username=jsonobject2.getString("username");
		Integer voteid=Integer.parseInt(jsonobject2.getString("voteid"));
		List<JSONObject> list=userSerivice.deletevote(voteid, username,session);
		JSONObject item = new JSONObject();
		session.close();
		for(int i=0;i<list.size();i++){		
			item.put(i, list.get(i));			
		}
		jsonobject2 = JSONObject.fromObject(item);	
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		return "jsonobject2";	
	}
	
	public String executed(String name,Session session){   
		jsonobject2 = JSONObject.fromObject(jsonStr);
		if(name.equals("my")){
			username=jsonobject2.getString("username");  //��ȡ���ݹ������û���
			List<JSONObject> list=userSerivice.executtriggerevent(username, name, 0, session);
			session.close();
			JSONObject item = new JSONObject();
			for(int i=0;i<list.size();i++){		
				item.put(i, list.get(i));			
			}
			jsonobject2 = JSONObject.fromObject(item);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			return "jsonobject2";
		}else if(name.equals("view")){
			Integer voteid=Integer.parseInt(jsonobject2.getString("voteid"));
			username=jsonobject2.getString("username");
			List<JSONObject> list=userSerivice.executtriggerevent(username, name, voteid, session);
			session.close();
			JSONObject item = new JSONObject();
			for(int i=0;i<list.size();i++){		
				item.put(i, list.get(i));			
			}
			jsonobject2 = JSONObject.fromObject(item);
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			return "jsonobject2";
		}else{
			return "jsonobject2";
		}		
    }
	
	
	public String addvote(String ip,String place,String name,Session session){   //�û�ͶƱ ����������vote ���б���������Ϊ��,��Ϊû����
		jsonobject2 = JSONObject.fromObject(jsonStr);   //����ȡ����json�ַ������json����
		username=jsonobject2.getString("username");    //ת������
		Integer voteid=Integer.parseInt(jsonobject2.getString("voteid"));
		Integer optionid=Integer.parseInt(jsonobject2.getString("optionid"));
		CreatvoteDate=new Date();
		choosetype=jsonobject2.getString("choosetype");
		CreatUser vote=new CreatUser();
		List<option> list2=new ArrayList<option>();
		User user=new User();
		vote vote2=new vote(username,voteid,optionid,CreatvoteDate.getTime(),ip,place);
		List<JSONObject> list4=userSerivice.add(vote, list2, user, name, vote2, choosetype,session); 
		session.close();
		JSONObject item = new JSONObject();
		for(int i=0;i<list4.size();i++){		
			item.put(i, list4.get(i));			
		}
		jsonobject2 = JSONObject.fromObject(item);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		return "jsonobject2";
		
	}
	
	
	public String  add(String name,Session session){       //����ͶƱ ������ȥcreat
		jsonobject2 = JSONObject.fromObject(jsonStr);   //����ȡ����json�ַ������json����
		username=jsonobject2.getString("username");    //ת������
		Votetitle=jsonobject2.getString("Votetitle");
		Votecontent=jsonobject2.getString("Votecontent");
		CreatvoteDate=new Date();
		EndvoteDate=jsonobject2.getString("EndvoteDate");
		Long endvotedate=Long.parseLong(EndvoteDate);   //ת��ʱ�������
		place=jsonobject2.getString("voteplace");
		choosetype=jsonobject2.getString("choosetype");
		Userentry=jsonobject2.getString("Userentry");
		CreatUser vote =new CreatUser(username,Votetitle,Votecontent,CreatvoteDate.getTime(),endvotedate,place,choosetype);
		String option5  = jsonobject2.getString("VoteOption");   //���ѡ�������
		JSONArray obj2 = JSONArray.fromObject(option5);
		List<option> list2=new ArrayList<option>();     
		int num=executed.Maxvoteid(username, session);   //��ȡ�û�����ͶƱ��voteid 
		for (int i = 0; i < obj2.size(); i++) {    //ѭ�����е�JSONArray����,ת�����ַ������洢���ݿ�
			String optionname=obj2.getString(i);
			option option=new option(num,optionname);
			list2.add(option);
        }  
		User user=new User(username,Userentry);
		vote vote4=new vote();
		List<JSONObject> list4=userSerivice.add(vote, list2, user, name, vote4, choosetype,session);   //�û�������ͶƱ����my��ť�Ĳ�ѯ
		session.close();
		JSONObject item = new JSONObject();
		for(int i=0;i<list4.size();i++){		
			item.put(i, list4.get(i));			
		}
		jsonobject2 = JSONObject.fromObject(item);
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		return "jsonobject2";
		
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getVoteOption() {
		return VoteOption;
	}
	public void setVoteOption(String voteOption) {
		VoteOption = voteOption;
	}
	public String getEndvoteDate() {
		return EndvoteDate;
	}
	public void setEndvoteDate(String endvoteDate) {
		EndvoteDate = endvoteDate;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getChoosetype() {
		return choosetype;
	}
	public void setChoosetype(String choosetype) {
		this.choosetype = choosetype;
	}
	public String getUserentry() {
		return Userentry;
	}
	public void setUserentry(String userentry) {
		Userentry = userentry;
	}
	public JSONObject getJsonobject2() {
		return jsonobject2;
	}
	public void setJsonobject2(JSONObject jsonobject2) {
		this.jsonobject2 = jsonobject2;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
