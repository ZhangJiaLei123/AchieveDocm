package com.jeeplus.modules.tcourse.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseInfo {

	private String id;
	private String userName;
	private Date stuStartTime;
	private Date stuEndTime;
	private String courName;
	private String courDes;
	private String timeInterval;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStuStartTime() {
		return stuStartTime;
	}
	public void setStuStartTime(Date stuStartTime) {
		this.stuStartTime = stuStartTime;
	}
	public Date getStuEndTime() {
		return stuEndTime;
	}
	public void setStuEndTime(Date stuEndTime) {
		this.stuEndTime = stuEndTime;
	}
	public String getCourName() {
		return courName;
	}
	public void setCourName(String courName) {
		this.courName = courName;
	}
	public String getCourDes() {
		return courDes;
	}
	public void setCourDes(String courDes) {
		this.courDes = courDes;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		   try  
		    {  
		      Date d1 = df.parse("2004-03-26 13:31:40");  
		      Date d2 = df.parse("2004-01-02 11:30:24");  
		      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
		      long days = diff / (1000 * 60 * 60 * 24);  
		   
		      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);  
		      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  
		      System.out.println(""+days+"天"+hours+"小时"+minutes+"分");  
		    }catch (Exception e)  
		    {  
		  }  

	}
}
