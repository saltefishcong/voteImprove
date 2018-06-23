package com.vote.Dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vote.eity.CreatUser;
import net.sf.json.JSONObject;

@Component("filter")
public class filter {
	
	private String ip;  //用户ip
	
	private String position;  //用户地理位置
	
	@Autowired
	private executed executed;
	
	public  String getLocalIp(HttpServletRequest request) {   //获取用户ip
		String remoteAddr = request.getRemoteAddr();
	    String forwarded = request.getHeader("X-Forwarded-For");
	    String realIp = request.getHeader("X-Real-IP");
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
     }
	 
	
	public String getposition(String ip) throws Exception{  //获取用户地理位置
		BufferedReader in = null; 
		String result = ""; 
		URL url = new URL("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip);   //发起http请求打开网址	
		URLConnection connection = url.openConnection();    //打开连接
	    connection.setRequestProperty("accept", "*/*"); 
		connection.setRequestProperty("connection", "Keep-Alive"); 
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
		connection.connect();    //开始链接
		Map <String ,List<String>>map = connection.getHeaderFields(); 
		in = new BufferedReader(new InputStreamReader( connection.getInputStream()));
		String line; 
		while ((line = in.readLine()) != null) { 
			result += line; 
			}
		in.close();    //关闭输入缓存流
		System.out.println(result);
		JSONObject  objdect=JSONObject.fromObject(result);
		System.out.println(objdect);
		String country=(String) objdect.get("country"); 
		String city="";
		String province=(String) objdect.get("province");
		System.out.println(province);
		if(!objdect.get("city").equals(province)){
	    city=(String) objdect.get("city"); 
		}
		position=country+province+city;   
		return position;
	}
	
	public boolean intercept(String jsonStr){   //防注入
		String string[]={"select","delete","update","insert","or","exec","and","count","%","*"};
		JSONObject jsonobject2 = JSONObject.fromObject(jsonStr);   //将获取到的json字符串变成json对象
		String username=jsonobject2.getString("username");    //转化数据
		String Votetitle=jsonobject2.getString("Votetitle");
		String Votecontent=jsonobject2.getString("Votecontent");
		String EndvoteDate=jsonobject2.getString("EndvoteDate");
		String Userentry=jsonobject2.getString("Userentry");
		String choosetype=jsonobject2.getString("choosetype");
		String place=jsonobject2.getString("voteplace");
		for(int i=0;i<string.length;i++){
		if(username.indexOf(string[i])!=-1){
			return false;
		}
		if(Votetitle.indexOf(string[i])!=-1){
			return false;
		}
		if(Votecontent.indexOf(string[i])!=-1){
			return false;
		}
		if(Userentry.indexOf(string[i])!=-1){
			return false;
		}
		if(EndvoteDate.indexOf(string[i])!=-1){ 
			return false;
		}
		if(choosetype.indexOf(string[i])!=-1){
			return false;
		}
		if(place.indexOf(string[i])!=-1){
			return false;
		}
	  }	
		return true;
	}	
	
	public boolean interceHours(Date date){                //判断是否处于每天的投票时间7:00-23:00
		if(date.getHours()<23&&date.getHours()>7){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean interceENDdate(Integer voteid,Session session ){		 //判断投票时间是否超过了指定的时间
		List<CreatUser> list=executed.CreatUser(voteid, session);  
		//查询创建投票的地址和用户的投票地址是不是一样
		CreatUser rCreatvoting=list.get(0);
		Date date=new Date();
		Long ENDdate=rCreatvoting.getEndvoteDate();
		if(date.getTime()<ENDdate){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean interceptposition(HttpServletRequest request,String getposition,Integer voteid ,Session session )  throws Exception{
		ip=this.getLocalIp(request);    //获得用户的ip
		position=this.getposition(ip);  //获得用户ip的地理位置
		System.out.println("获取的地理位置"+position);
		if(!getposition.equals(position)){   //判断和传过来的地理位置是否相等
			System.out.println("和传过来的地理位置不相等"+getposition);
		return false;
		}
		List<CreatUser> list=executed.CreatUser(voteid, session);
		//查询创建投票的地址和用户的投票地址是不是一样
		CreatUser rCreatvoting=list.get(0);
		String place=rCreatvoting.getVotepalace();
		if(position.equals(place)){  //是就能投票
			return true;
		}else{
			System.out.println("和投票的地理位置不相等"+position);
		  return false;	 //不是就返回为假
		  
		}		
	}
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
