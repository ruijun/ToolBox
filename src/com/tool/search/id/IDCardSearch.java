package com.tool.search.id;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class IDCardSearch {

	static StringBuilder sb;
	static String baseUri = "http://www.youdao.com/smartresult-xml/search.s?type=id&q=";
	public static String idCardSearch(String str){
		
		try {
			sb =new StringBuilder("");
			//实例化URL
			URL url = new URL(baseUri+str);
			//使用HttpURLConnection打开连接  
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(); 
			httpConnection.setRequestMethod("GET"); 
			httpConnection.setRequestProperty("Charset", "GBK");
			//得到输入流对象
		    InputStream in = httpConnection.getInputStream();
		    List<IDCard> ids = IDcardPullService.readXml(in);
			
			for (IDCard id : ids) {
				sb.append(id.toString()).append("\n");
			}	
			
			in.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();		
	}
}
