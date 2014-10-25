package com.tool.timing.message;

import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;

public class SMSActivity extends Activity {

	private static final String save="saved_pref";
	private static final String num="number";
	private static final String msn="message";
	
	String ednum=null;
	String edmsn=null;           
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences sp=getSharedPreferences(save,0);
		
		ednum=sp.getString(num, "");
		edmsn=sp.getString(msn, "");///读取短信息
		
		StringTokenizer nums = new StringTokenizer(ednum, ",");
		//SmsManager sm=SmsManager.getDefault();
		if (ednum.length()<2) {
			System.out.println(edmsn);
			//sm.sendTextMessage(ednum,null,edmsn,null, null);
			sendSMS(ednum,edmsn);
		} else {
			while(nums.hasMoreTokens()){
				String eachNum = nums.nextToken();
				System.out.println(eachNum);
				//sm.sendTextMessage(eachNum,null,edmsn,null, null);
				sendSMS(eachNum,edmsn);
			}
		}				           		           
		SMSActivity.this.finish();
	}
	
	 //分条发送短信
    public void sendSMS(String num,String message){
        SmsManager smsMng = SmsManager.getDefault();      
        //按照一条短信，最大容量拆分成多条普通短信
        List<String> divideContents = smsMng.divideMessage(message);
        for(String text : divideContents){
            smsMng.sendTextMessage(num, null, text, null, null);
        }
    }   
//    
//    //发送长短信
//    public void sendMultipartTextMessage(){
//        SmsManager smsMng = SmsManager.getDefault();
//        ArrayList<String> sendArray = smsMng.divideMessage(message);
//        smsMng.sendMultipartTextMessage(m_sendTel, null, sendArray, null, null);
//    }

	
}
