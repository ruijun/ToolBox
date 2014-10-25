package com.tool.convert.rmb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tool.R;
import com.tool.common.ActivityUtils;

public class ConvertRMB extends Activity {

	private ImageView convertBtn;
	private EditText rmbTx;
	private TextView conertResultTx;
	private static final int MESSAGETYPE = 101;
    private ProgressDialog progressDialog = null; 
    String resultData;
    StringBuilder sb;
    StringBuffer stringBuffer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.convert_rmb);
		convertBtn = (ImageView)findViewById(R.id.convert_search_btn);
        rmbTx = (EditText)findViewById(R.id.rmb_tx);
        rmbTx.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        conertResultTx = (TextView)findViewById(R.id.convert_result);
        
        convertBtn.setOnClickListener(new ConvertURLListener());
	}	
	
	class ConvertURLListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			resultData="";
			String str=ActivityUtils.deleteSpace(rmbTx.getText().toString());
			System.out.println(str+"--------->");
			// TODO Auto-generated method stub
			if(ActivityUtils.validateNull(str)){
//				try {
//					Integer.parseInt(str);
//					convertRMB(str);
//				} catch (Exception e) {
//					// TODO: handle exception
//					ActivityUtils.showDialog(ConvertRMB.this, "确定", "提示", "输入的不正确");
//				}
				convertRMB(str);
			}else {
				
				ActivityUtils.showDialog(ConvertRMB.this, "确定", "提示", "输入的人民币数目不能为空");
			}			
		}
	}
	
    public void convertRMB(final String str) {
    	
		final String baseUri = "http://api.liqwei.com/currency/?exchange=CNY|USD&count=";
		progressDialog = ProgressDialog.show(ConvertRMB.this, "转换", "正在转换,请稍候......");  
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

            					resultData += readline + "\n";// 注意：存储数据使用的是全局变量，因为在handler中也要使用到
            				}
            				resultData ="换算的得到的美元为:\n"+resultData;
	        				// 资源请求结束，需要做最后的显示：传递处理后的消息给Handler
	        				Message msg_listData = new Message();
	                        msg_listData.what = MESSAGETYPE;
	                        msg_listData.obj = resultData;
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
        	    
        	    if (message.obj=="") {
            		if(progressDialog.isShowing()){
            			progressDialog.dismiss(); //关闭进度条
            			showExcetionDialog("注意", "获取数据出现错误\n"+"请检查你输入是否正确");         			
            		}
               		return;
    			}
                switch (message.what) {
                case MESSAGETYPE:                                        
                	    conertResultTx.setText(resultData);                
                        progressDialog.dismiss(); //关闭进度条
                        break;
                }
            }
        };
        
        public void showExcetionDialog(String title,String message){

    		AlertDialog dialog;
    		AlertDialog.Builder builder = new AlertDialog.Builder(ConvertRMB.this);
    		builder.setTitle(title);
    		builder.setMessage(message);
    		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				conertResultTx.setText("没有查询到相关结果");
    			}					
    		});
    		dialog = builder.create();
    		dialog.show();
       }
}
