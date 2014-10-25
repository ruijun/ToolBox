package com.tool.search.creditcard;

import java.io.Serializable;

public class CreditCard implements Serializable{

	private String cardNum;
	private String area;
	private String bank;
	private String cardType;
	private String cardName;
	private String error;
	
	public String getCardNum() {
		return cardNum;
	}
	
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String toString(){
		return "银行卡号:"+this.cardNum+"\r\n"+
	            "地区:"+this.area+"\r\n"+
				"银行:"+this.bank+"\r\n"+
	            "卡类型:"+this.cardType+"\r\n"+
	            "卡名:"+this.cardName+"\r\n";
	}
}
