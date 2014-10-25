package com.tool.convert.url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tool.R;
import com.tool.common.ActivityUtils;
/*
 * 网址转换的Activity
 */
public class ConvertURL extends Activity {
	private ImageView convertBtn;
	private EditText urlTx;
	private TextView conertResultTx;
	private static final int MESSAGETYPE = 101;
    private ProgressDialog progressDialog = null; 
    String resultData="";
    StringBuilder sb;
    StringBuffer stringBuffer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.convert_url);
        
        convertBtn = (ImageView)findViewById(R.id.convert_search_btn);
        urlTx = (EditText)findViewById(R.id.url_tx);
        conertResultTx = (TextView)findViewById(R.id.convert_result);
        
        convertBtn.setOnClickListener(new ConvertURLListener());
	}	
	
	class ConvertURLListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = ActivityUtils.deleteSpace(urlTx.getText().toString());
			if(ActivityUtils.isNetworkAvailable(ConvertURL.this)==false){
				Toast.makeText(ConvertURL.this, "请确认网络是否已经连接",Toast.LENGTH_LONG).show();
			}
			else if(ActivityUtils.isNetworkAvailable(ConvertURL.this)==true){
				if(ActivityUtils.validateNull(str)){
					if(str.length()==11){
						convertURL(str);
					}else {
						ActivityUtils.showDialog(ConvertURL.this, "确定", "提示", "请输入正确的网址");
					}
											
				}else{
					
	    			ActivityUtils.showDialog(ConvertURL.this, "确定", "提示", "输入的网址不能为空");
	    		}				 
		    }

	};	
}
	
    public void convertURL(final String str) {
        
		final String baseUri = "http://api.liqwei.com/shorter/?url=";
		progressDialog = ProgressDialog.show(ConvertURL.this, "转换", "正在转换,请稍候......");  
			 new Thread() {
	                public void run() {                        
	                	String readline = "";
	        			try {
	        				URL url = new URL(baseUri+str);
	        				Log.i("TAG", url.toString());
	        				//使用HttpURLConnection打开连接  
	        				HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(); 
	        				InputStreamReader is = new InputStreamReader(
	        						httpConnection.getInputStream());
            				BufferedReader br = new BufferedReader(is);

            				while (((readline = br.readLine()) != null)) {

            					resultData += readline + "\n";
            				}
            				//resultData ="转换后的网址为:\n"+resultData;
	        				// 资源请求结束，需要做最后的显示：传递处理后的消息给Handler
	        				Message msg_listData = new Message();
	                        msg_listData.what = MESSAGETYPE;
	                        idHandler.sendMessage(msg_listData);

	        				// 关闭资源
	                        br.close();
	                        is.close();
	        				httpConnection.disconnect();
	        			} catch (Exception e) {
	        				throw new RuntimeException(e.getMessage(),e);
	        			}                                                      
	                }
	        }.start();
		} 

      Handler idHandler = new Handler() {                

        public void handleMessage(Message message) {
                switch (message.what) {
                case MESSAGETYPE:                                        
                	    conertResultTx.setText(resultData);
                	    //关闭进度条
                        progressDialog.dismiss(); 
                        break;
                }
            }
        };
}
