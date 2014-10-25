package com.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tool.search.alexa.AlexaActivity;
import com.tool.search.creditcard.CreditCardActivity;
import com.tool.search.id.IDCardActivity;
import com.tool.search.ip.IpActivity;
import com.tool.search.phone.PhoneActivity;
import com.tool.search.postcode.PostCodeActivity;
/*
 * 查询工具模块的Activity
 */
public class QueryTool extends BaseActivity implements OnClickListener {

	private ImageView ipView,idView,phoneView,postCodeView,bankView,alexaView,homeBtn,aboutBtn;
	StringBuilder sb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.query);
		
		initBottomView();
		setBottomListener();
	}
	public void  initBottomView(){
		
		homeBtn = (ImageView)findViewById(R.id.home);
		aboutBtn = (ImageView)findViewById(R.id.reback);
		idView = (ImageView)findViewById(R.id.query_id);
		ipView = (ImageView)findViewById(R.id.query_ip);
		phoneView = (ImageView)findViewById(R.id.query_phone);
		postCodeView = (ImageView)findViewById(R.id.query_postcode);
		bankView = (ImageView)findViewById(R.id.query_bank);
		alexaView = (ImageView)findViewById(R.id.query_alexa);
		
	}
	public void setBottomListener(){
		
		homeBtn.setOnClickListener(this);
		aboutBtn.setOnClickListener(this);
		idView.setOnClickListener(this);
		ipView.setOnClickListener(this);
		phoneView.setOnClickListener(this);
		postCodeView.setOnClickListener(this);	
		bankView.setOnClickListener(this);
		alexaView.setOnClickListener(this);
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 if (keyCode == KeyEvent.KEYCODE_BACK){
			 Intent intent = new Intent(QueryTool.this, MainActivity.class);
			 startActivity(intent);
			 QueryTool.this.finish();
		 }
		 return false;
	 }
	
	public void onClick(View v){
		//回到首页的按钮
		if (v==homeBtn) {
			Intent intent = new Intent(QueryTool.this, MainActivity.class);			
			startActivity(intent);	
			QueryTool.this.finish();
		}
		//有关模块信息的按钮
		else if (v==aboutBtn) {
			showAboutDialog("关于",
		                    "1.身份证号\n"+"输入身份证号,查询出身份证号的归属地\n"
					        +"2.IP地址\n"+"输入身份证号,查询出IP地址的归属地\n"
					        +"3.手机号码\n"+"输入手机号,查询出手机号的归属地\n"
					        +"4.区号查询\n"+"输入区号,查询出该区号的省份,城市,邮编和归属地\n"
					        +"5.银行卡号\n"+"输入银行卡号,查询出该卡号所属地区,银行,卡类型,卡名\n"
					        +"6.Alexa查询\n"+"输入网址,查询出该网站的全球排名,访问速度,反向链接等信息\n");
		}
		//身份证查询
		else if (v==idView) {
			Intent intent = new Intent(QueryTool.this, IDCardActivity.class);
			startActivity(intent);
			QueryTool.this.finish();
		}
		//IP查询
		else if(v==ipView){			
			Intent intent = new Intent(QueryTool.this, IpActivity.class);
			startActivity(intent);	
			QueryTool.this.finish();
		}	
		//手机号码查询
		else if(v==phoneView){			
			Intent intent = new Intent(QueryTool.this, PhoneActivity.class);
			startActivity(intent);	
			QueryTool.this.finish();			
		}
		//区号查询
		else if(v==postCodeView){			
			Intent intent = new Intent(QueryTool.this, PostCodeActivity.class);
			startActivity(intent);	
			QueryTool.this.finish();
		}	
		//银行卡号查询
        else if (v==bankView) {
        	Intent intent = new Intent(QueryTool.this, CreditCardActivity.class);
			startActivity(intent);
			QueryTool.this.finish();
		}
		//Alexa有关信息查询
        else if (v==alexaView) {
        	Intent intent = new Intent(QueryTool.this, AlexaActivity.class);
			startActivity(intent);
			QueryTool.this.finish();
		}
	}	
	
	//关于查询工具模块有关信息的对话框
	public void showAboutDialog(String title,String message){

//		AlertDialog dialog;
//		AlertDialog.Builder builder = new AlertDialog.Builder(QueryTool.this);
//		builder.setIcon(android.R.drawable.ic_dialog_info);
//		builder.setTitle(title);
//		builder.setMessage(message);
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				
//			}					
//		});
//		dialog = builder.create();
//		dialog.show();
		
		 final Dialog aboutDialog = new Dialog(QueryTool.this,
					R.style.dialog_style);
			//lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   aboutDialog.setContentView(R.layout.about_dialog);
			((TextView) aboutDialog.findViewById(R.id.dialog_title)).setText(title);
			((TextView) aboutDialog.findViewById(R.id.dialog_message)).setText(message);
			
			((Button) aboutDialog.findViewById(R.id.dialog_button_ok))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// write your code to do things after users clicks CANCEL
							aboutDialog.dismiss();
						}
					});
			aboutDialog.show();
   }
}