package com.tool.search.id;

import java.io.Serializable;

public class IDCard implements Serializable{

	private String code;
	private String location;
	private String birthday;
	private String gender;
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	public String getGender(){
		return gender;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String toString(){
		return "身份证号:"+this.code+"\r\n"+
	            "地址:"+this.location+"\r\n"+
				"出生年月:"+this.birthday+"\r\n"+
	            "性别:"+this.gender+"\r\n";
	}
}
