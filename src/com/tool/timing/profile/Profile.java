package com.tool.timing.profile;

import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tool.MainActivity;
import com.tool.R;
import com.tool.common.ToastManager;

public class Profile extends Activity {

	private TextView profileTx;
	private ImageView homeBtn,rebackBtn; 
	String TAG="Profile";
	protected boolean isChange;	
	AlarmManager alarms;
	TimePicker tp ;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile);
        
        alarms = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        tp = (TimePicker)findViewById(R.id.timePkr);
        profileTx = (TextView)findViewById(R.id.query_tool);
        homeBtn = (ImageView)findViewById(R.id.home);
		rebackBtn = (ImageView)findViewById(R.id.reback);
        profileTx.setText(getResources().getString(R.string.timing_profile));
        tp.setIs24HourView(true);
        
        homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Profile.this, MainActivity.class);
				startActivity(intent);
				Profile.this.finish();
			}
		});
		
		rebackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Profile.this, MainActivity.class);
				startActivity(intent);
				Profile.this.finish();
			}
		});
        
        //添加onChangeListener
        RadioGroup group = (RadioGroup) findViewById(R.id.menu);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (isChange)
                                        return;
                                String string ="";
                                switch (checkedId) {
                                        case R.id.ring_and_vibrate: 
                                        	string="ring_and_vibrate";
                                        	ringAndVibrate(); 
                                        	break;
                                        case R.id.ring: 
                                        	string="ring";
                                        	ring(); 
                                        	break;
                                        case R.id.vibrate: 
                                        	string="vibrate";
                                        	vibrate(); 
                                        	break;
                                        case R.id.silent: 
                                        	string="vibrate";
                                        	silent(); 
                                        	break;
                                }
                                
		                        RadioButton radio = (RadioButton) findViewById(checkedId);
		                        if (radio != null)
		                        	//radio.setTextSize(30);
		                        	ToastManager.showToastView(Profile.this, "你设置了要切换的模式为:"+string);
                        }
        		});
        //RadioButton添加监听器
        for (int i = 0, l = group.getChildCount(); i < l; i++) {
                RadioButton radio = (RadioButton) group.getChildAt(i);
                radio.setOnTouchListener(new OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                		RadioButton radio = (RadioButton) v;
                                        if (!radio.isChecked())
                                                return false;
                                        
                                        return false;
                                }
                });
        }
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		updateRadioGroup();
	}
	//更新情景模式
    protected void updateRadioGroup() {
        int checkedId = currentMode();
        RadioButton checked = (RadioButton) findViewById(checkedId);
        isChange = true;
        checked.setChecked(true);
        isChange = false;
    }
    //取得当前情景模式
    protected int currentMode() {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        
        switch (audio.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT: return R.id.silent;
                case AudioManager.RINGER_MODE_VIBRATE: return R.id.vibrate;
        }
        
        if (audio.shouldVibrate(AudioManager.VIBRATE_TYPE_RINGER))
                return R.id.ring_and_vibrate;
        
        return R.id.ring;
    }
    //铃声和震动
    protected void ringAndVibrate() {
    	Log.e(TAG,""+R.id.ring_and_vibrate);
    	Intent intent = new Intent(AlarmBroadcastReceiver.RV_CHANGED);
    	intent.putExtra("checkedId", R.id.ring_and_vibrate);
    	PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				AlarmBroadcastReceiver.REQUEST_CODE,
				intent,
				0);
    	Log.e(TAG,""+intent);
    	alarms.set(AlarmManager.RTC_WAKEUP,getTime(), alarmIntent);
    }
  //铃声
    protected void ring() {
    	Log.e(TAG,""+R.id.ring);
    	Intent intent = new Intent(AlarmBroadcastReceiver.RING_CHANGED);
    	intent.putExtra("checkedId", R.id.ring);
    	PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				AlarmBroadcastReceiver.REQUEST_CODE,
				intent,
				0);
    	Log.e(TAG,""+intent);
    	alarms.set(AlarmManager.RTC_WAKEUP,getTime(), alarmIntent);
    }
  //震动
    protected void vibrate() {
    	Log.e(TAG,""+R.id.vibrate);
    	Intent intent = new Intent(AlarmBroadcastReceiver.VIBRATE_CHANGED);
    	intent.putExtra("checkedId", R.id.vibrate);
    	PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				AlarmBroadcastReceiver.REQUEST_CODE,
				intent,
				0);
    	Log.e(TAG,""+intent);
    	alarms.set(AlarmManager.RTC_WAKEUP,getTime(), alarmIntent);
    }
  //静音
    protected void silent() {
    	Log.e(TAG,""+R.id.silent);
    	Intent intent = new Intent(AlarmBroadcastReceiver.SILENT_CHANGED);
    	intent.putExtra("checkedId", R.id.silent);
    	PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				AlarmBroadcastReceiver.REQUEST_CODE,
				intent,
				0);
    	Log.e(TAG,""+intent);
    	alarms.set(AlarmManager.RTC_WAKEUP,getTime(), alarmIntent);
    }
    
    //计算切换时间
    private long getTime() {
    	Date dateNow = new Date();
    	long hour = tp.getCurrentHour()-dateNow.getHours();
    	long min = tp.getCurrentMinute()-dateNow.getMinutes();
    	long second = dateNow.getSeconds();
    	return dateNow.getTime() + (hour*60 + min)*60*1000 - second*1000;
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event){
 		 if (keyCode == KeyEvent.KEYCODE_BACK){
 			 Intent intent = new Intent(Profile.this, MainActivity.class);
 			 startActivity(intent);
 			 Profile.this.finish();
 		 }
 		 return false;
 	 }
}

