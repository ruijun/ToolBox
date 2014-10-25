package com.tool.search.alexa;

import java.io.Serializable;

public class Alexa implements Serializable{

	private int ID;
	private String title;
	private String rank;
	private String referrers;
	private String msSpeed;
	private String second;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getReferrers() {
		return referrers;
	}
	public void setReferrers(String referrers) {
		this.referrers = referrers;
	}
	public String getMsSpeed() {
		return msSpeed;
	}
	public void setMsSpeed(String msSpeed) {
		this.msSpeed = msSpeed;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String toString(){
		return  "域名名称:"+this.title+"\r\n"+
				"全球网站排名:"+this.rank+"\r\n"+
	            "反向链接:"+this.referrers+"\r\n"+
	            "访问速度:"+this.msSpeed+"Ms"+this.second+"seconds";
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
