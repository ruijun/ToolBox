package com.tool;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity{
	
	private TextView mVersion;
	private Button mUpdateBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		
		//获取客户端版本信息
        try { 
        	PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        	mVersion = (TextView)findViewById(R.id.about_version);
    		mVersion.setText("版本："+info.versionName);
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
        
        mUpdateBtn = (Button)findViewById(R.id.about_update);
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//UpdateManager.getUpdateManager().checkAppUpdate(About.this, true);
				Intent intent=new Intent(Intent.ACTION_SEND);
				intent.setType("image/*");//intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享软件");
				intent.putExtra(Intent.EXTRA_TEXT, "亲们，我正在使用Android信息收集与查询的软件，很实用哦，大家也来试试哇！");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getTitle()));
			}
		});        
	}
}