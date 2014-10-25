package com.tool.common;


import android.util.Log;
/**
 * 日志管理
 * @author admin
 *
 */
public class LogManager {

	public static boolean debug = true;
	public static void i(String tag,String msg){
		if(debug){
		 Log.i(tag, msg);
		}
	}
	public static void i(String tag,String msg,Throwable t){
		if(debug){
		Log.i(tag, msg,t);
		}
	}
	public static void d(String tag,String msg,Throwable t){
		if(debug){
		Log.d(tag, msg,t);
		}
	}
	public static void d(String tag,String msg){
		if(debug){
		Log.d(tag, msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(debug){
		Log.e(tag, msg);
		}
	}
	public static void e(String tag,String msg,Throwable t){
		if(debug){
		Log.e(tag, msg,t);
		}
	}
	public static void w(String tag,String msg){
		if(debug){
		Log.w(tag, msg);
		}
	}
	public static void w(String tag,String msg,Throwable t){
		if(debug){
		Log.w(tag, msg,t);
		}
	}
}
