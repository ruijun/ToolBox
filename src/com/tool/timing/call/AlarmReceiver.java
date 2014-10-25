package com.tool.timing.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {

	private Context context;

	private String telNumber = "13800138000";

	public void onReceive(Context context, Intent intent) {
		this.context = context;
		SharedPreferences preference =context.getSharedPreferences("zhuyan",
				Context.MODE_PRIVATE);
		telNumber=preference.getString("tel_phone",telNumber);
		PhoneCall();
	}

	private void PhoneCall() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Uri localUri = Uri.parse("tel:" + telNumber);
		Intent call = new Intent(Intent.ACTION_CALL, localUri);
		call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(call);
		// android.util.Log.v("TeleListener", "start to call");
	}

}
