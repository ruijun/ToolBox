package com.tool.search.postcode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class PostCodeSearch {

	static String page;
	static String baseUri = "http://shinylife.net/api/BaiBaoXianApi.ashx?t=zip&q=";
	public static String postCodeSearch(String str){
		try {
			//BufferedReader in = null;
			//实例化URL
			String url = baseUri+str;
			Log.i("TAG", url);
			HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
//            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
//            	Log.i("TAG", "请求服务器端成功");
//            	in = new BufferedReader(
//                        new InputStreamReader(
//                    	    response.getEntity().getContent()));
//                
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//                String NL = System.getProperty("line.separator");
//                while ((line = in.readLine()) != null) {
//                    sb.append(line + NL);
//                }
//                in.close();
//
//                page = sb.toString();
//                Log.i("TAG", page);
            	page = EntityUtils.toString(response.getEntity()); 
            	System.out.println(page); 
            	if (page != null && page.length() > 0) {
            		PostCode postCode = new PostCode();
    				JSONObject jb = new JSONObject(page);
    				postCode.setLocation(jb.getString("location"));
    				postCode.setPhone(jb.getString("phone"));
    				postCode.setZipcode(jb.getString("zipcode")); 
    				return postCode.toString();
    			}
                    				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("TAG", postCode.toString());	
		return null;
	}
}
