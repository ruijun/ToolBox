package com.tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tool.common.ActivityUtils;
/*
 * WIFI信息模块的Activity
 */
@TargetApi(5)
public class WIFIInfo extends BaseActivity {

	private TextView wifiTile;
	private ImageView homeBtn,rebackBtn;
	private TextView notWifi,bssidTx,ipAdressTx,linkSpeedTx,macAdressTx,ssidTx,netWorkIDTx;
	private LinearLayout wifiLayout,aboutLayout;
    private WifiManager mWifiManager;  
    private WifiInfo mWifiInfo;  
    WifiConfiguration mWifiConfiguration;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wifi);
		
		initView();
		setLisener();
		
		//调用ActivityUtils的静态方法判断手机是否连接上WIFI
		if (ActivityUtils.isWifiAvailable(this)==true) {
			//连接上WIFI，显示有关WIFI的信息
			getWifiInfo();
		} else {
			//没有连接上WIFI，显示其他信息
			notWifi.setVisibility(View.VISIBLE);
		}
	}
	
	public void initView(){
		wifiTile = (TextView)findViewById(R.id.query_tool);
		wifiTile.setText(getResources().getString(R.string.wifi_info));
		homeBtn = (ImageView)findViewById(R.id.home);
		rebackBtn = (ImageView)findViewById(R.id.reback);
		wifiLayout = (LinearLayout)findViewById(R.id.wifi_layout);
		aboutLayout = (LinearLayout)findViewById(R.id.about_layout);
		notWifi = (TextView)findViewById(R.id.not_wifi_tx);
		bssidTx = (TextView)findViewById(R.id.bssid_tx);
		ipAdressTx = (TextView)findViewById(R.id.ip_adress);
		linkSpeedTx = (TextView)findViewById(R.id.link_speed);
		macAdressTx = (TextView)findViewById(R.id.mac_adress);
		ssidTx = (TextView)findViewById(R.id.ssid_tx);
		netWorkIDTx = (TextView)findViewById(R.id.network_id);
	}
	
	public void setLisener(){
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WIFIInfo.this, MainActivity.class);
				startActivity(intent);
				WIFIInfo.this.finish();
			}
		});
		
		rebackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WIFIInfo.this, MainActivity.class);
				startActivity(intent);
				WIFIInfo.this.finish();
			}
		});
	}
	
	//获得WIFI信息的方法
	public void getWifiInfo(){
		//取得WifiManager对象  
        mWifiManager = (WifiManager)this.getSystemService(WIFI_SERVICE);  
        //取得WifiInfo对象  
        mWifiInfo = mWifiManager.getConnectionInfo();
        //实例化WifiConfiguration对象
        mWifiConfiguration = new WifiConfiguration();
        bssidTx.setText(mWifiInfo.getBSSID().toString());
        ipAdressTx.setText(String.valueOf(mWifiInfo.getIpAddress()));
        linkSpeedTx.setText(String.valueOf(mWifiInfo.getLinkSpeed())+mWifiInfo.LINK_SPEED_UNITS.toString());
        macAdressTx.setText(mWifiInfo.getMacAddress().toString());
        ssidTx.setText(mWifiInfo.getSSID().toString());
        netWorkIDTx.setText(String.valueOf(mWifiInfo.getNetworkId()));
		wifiLayout.setVisibility(View.VISIBLE);
		aboutLayout.setVisibility(View.VISIBLE);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		 if (keyCode == KeyEvent.KEYCODE_BACK){
			 Intent intent = new Intent(WIFIInfo.this, MainActivity.class);
			 startActivity(intent);
			 WIFIInfo.this.finish();
		 }
		 return false;
	 }
}
