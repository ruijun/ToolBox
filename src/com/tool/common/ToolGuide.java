package com.tool.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tool.BaseActivity;
import com.tool.MainActivity;
import com.tool.MobileInfo;
import com.tool.R;
/*
 * 软件的使用指南Activity
 */
public class ToolGuide extends BaseActivity {

	private TextView introduceTx,functionTx,noticeTx,titleTx;
	private ImageView homeBtn,rebackBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tool_guide);
        
        initView();
        introduceTx.setText(getResources().getText(R.string.about_software));
        functionTx.setText(getResources().getText(R.string.module_function));
        noticeTx.setText(getResources().getText(R.string.notice));
        titleTx.setText(getResources().getText(R.string.tool_guide));

		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ToolGuide.this, MainActivity.class);
				startActivity(intent);
				ToolGuide.this.finish();
			}
		});
		
		rebackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ToolGuide.this, MainActivity.class);
				startActivity(intent);
				ToolGuide.this.finish();
			}
		});
	}
	
	public void initView(){
		introduceTx = (TextView)findViewById(R.id.introduce_tx);
		functionTx = (TextView)findViewById(R.id.function_tx);
		noticeTx = (TextView)findViewById(R.id.notice_tx);
		titleTx = (TextView)findViewById(R.id.query_tool);
		homeBtn = (ImageView)findViewById(R.id.home);
		rebackBtn = (ImageView)findViewById(R.id.reback);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 if (keyCode == KeyEvent.KEYCODE_BACK){
			 Intent intent = new Intent(ToolGuide.this, MainActivity.class);
			 startActivity(intent);
			 ToolGuide.this.finish();			
		 }
		 return false;
	 }
}
