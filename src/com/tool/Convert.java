package com.tool;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.tool.convert.ascii.ConvertASCII;
import com.tool.convert.bmi.ConvertBMI;
import com.tool.convert.rgb.ConvertRGB;
import com.tool.convert.rmb.ConvertRMB;
import com.tool.convert.url.ConvertURL;
/*
 * 转换工具的Activity
 */
public class Convert extends TabActivity {

	private TextView convertTitle;
	private ImageView homeBtn,aboutBtn; 
	private RadioGroup group;
	private TabHost tabHost;
	public static final String TAB_ASCII="tabAscii";
	public static final String TAB_RGB="tabRgb";
	public static final String TAB_URL="tabUrl";
	public static final String TAB_RMB="tabSt";
	public static final String TAB_BMI="tabReturn";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.convert);
		group = (RadioGroup)findViewById(R.id.main_tab);
		convertTitle = (TextView)findViewById(R.id.query_tool);
		convertTitle.setText(getResources().getString(R.string.convert_tool));
		homeBtn = (ImageView)findViewById(R.id.home);
		aboutBtn = (ImageView)findViewById(R.id.reback);
		
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Convert.this, MainActivity.class);
				startActivity(intent);
				Convert.this.finish();
			}
		});
				
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_ASCII)
	                .setIndicator(TAB_ASCII)
	                .setContent(new Intent(this,ConvertASCII.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_RGB)
	                .setIndicator(TAB_RGB)
	                .setContent(new Intent(this,ConvertRGB.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_URL)
	    		.setIndicator(TAB_URL)
	    		.setContent(new Intent(this,ConvertURL.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_RMB)
                .setIndicator(TAB_RMB)
                .setContent(new Intent(this,ConvertRMB.class)));
	    tabHost.addTab(tabHost.newTabSpec(TAB_BMI)
                .setIndicator(TAB_BMI)
                .setContent(new Intent(this,ConvertBMI.class)));
	    group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_ASCII);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_RGB);
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag(TAB_URL);
					break;
				case R.id.radio_button3:
					tabHost.setCurrentTabByTag(TAB_RMB);
					break;
				case R.id.radio_button4:
					tabHost.setCurrentTabByTag(TAB_BMI);
					break;
				default:
					break;
				}
			}
		});
	    
    	aboutBtn.setOnClickListener(new OnClickListener() {		
		@Override
		public void onClick(View v) {
		
			if (tabHost.getCurrentTabTag()==TAB_ASCII) {
				Intent intent = new Intent(Convert.this, MainActivity.class);
				startActivity(intent);
				Convert.this.finish();
			}else if (tabHost.getCurrentTabTag()==TAB_RGB) {
				tabHost.setCurrentTabByTag(TAB_ASCII);
			}else if (tabHost.getCurrentTabTag()==TAB_URL) {
				tabHost.setCurrentTabByTag(TAB_RGB);
			}else if (tabHost.getCurrentTabTag()==TAB_RMB) {
				tabHost.setCurrentTabByTag(TAB_URL);
			}else if (tabHost.getCurrentTabTag()==TAB_BMI) {
				tabHost.setCurrentTabByTag(TAB_RMB);
			}				
		}
	});
  }
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 if (keyCode == KeyEvent.KEYCODE_BACK){
			 Intent intent = new Intent(Convert.this, MainActivity.class);
			 startActivity(intent);
			 Convert.this.finish();
		 }
		 return false;
	 }
}
