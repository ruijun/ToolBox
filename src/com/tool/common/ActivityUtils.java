package com.tool.common;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tool.MainActivity;
import com.tool.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public final class ActivityUtils {

	//对话框
	public static void showDialog(Context context, String button,String title, String message){

		   final Dialog aboutAuthorDialog = new Dialog(context,
					R.style.dialog_style);
			//lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   aboutAuthorDialog.setContentView(R.layout.about_dialog);
			((TextView) aboutAuthorDialog.findViewById(R.id.dialog_title)).setText(title);
			((TextView) aboutAuthorDialog.findViewById(R.id.dialog_message)).setText(message);
			
			((Button) aboutAuthorDialog.findViewById(R.id.dialog_button_ok))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// write your code to do things after users clicks CANCEL
							aboutAuthorDialog.dismiss();
						}
					});
			aboutAuthorDialog.show();
	}
	
	//判断输入的字符是否为空
	public static boolean validateNull(String str){
		if(str == null || str.equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateNumber(String str){
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	public static boolean validatePnumCount(String str){
		if (str.length()==11) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validatestr(String str){
		Pattern pattern = Pattern.compile("[0-9],.");
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	//判断输入的是否为字符
	public static boolean valiChar(String string){
		StringTokenizer s = new StringTokenizer(string, ",");
//		int num=0;
		  if(s.hasMoreTokens()){
			String str = s.nextToken();
            byte[] cc = str.getBytes(); 
            int a=(int)cc[0];
			if (a<32||a>127) {
				return false;
			}
		}
		return true;
	}
	
	//判断手机的网络是否连接
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = mConnMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mMobile = mConnMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean flag = false;
		if ((mWifi != null)&& ((mWifi.isAvailable()) || (mMobile.isAvailable()))) {
			if ((mWifi.isConnected()) || (mMobile.isConnected())) {
				flag = true;
			}
		}
		return flag;
	}
	
	//判断WIFI是否连接
	public static boolean isWifiAvailable(Context context) {
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = mConnMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		boolean flag = false;
		if ((mWifi != null)&& ((mWifi.isAvailable()))) {
			if ((mWifi.isConnected())) {
				flag = true;
			}
		}
		return flag;
	}
	
	//删除字符首尾的空格
	public static String deleteSpace(String str){
		if (str.startsWith(" ")) {
			str = str.substring(1, str.length()).trim();
		} 
		else if(str.endsWith(" ")) {
			str = str.substring(0, str.length()-1).trim();
		}
		
		return str;
	}
	
	//判断输入的字符是否为IP地址
	public static boolean isIP(String str){
		boolean b = false;
		if(str.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			String s[] = str.split("\\.");
			if(Integer.parseInt(s[0])<255)
				if(Integer.parseInt(s[1])<255)
					if(Integer.parseInt(s[2])<255)
						if(Integer.parseInt(s[3])<255)
							b = true;
		}
		return b;
	}
	
	//判断输入的字符中间是否有空格
	public static boolean midIsSpace(String str){
		boolean mat = str.matches("^(\\s|.*\\s+.*)$");
		if (mat) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
}
