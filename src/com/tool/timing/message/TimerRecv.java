package com.tool.timing.message;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;

public class TimerRecv extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){ 
          
        Intent itent = new Intent(context, SMSActivity.class);          
        Bundle bundleRet = new Bundle();   
        bundleRet.putString("STR_CALLER", "");   
        itent.putExtras(bundleRet);   
        itent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
        context.startActivity(itent);
    }
}


