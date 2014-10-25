package com.tool.common;

import com.tool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastManager {
	
	public static void show(Context context,String msg){
		
		 Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void showToastView(final Context context,String message){
		   LayoutInflater inflater=LayoutInflater.from(context);
		   View toaskView=null;
		   toaskView=inflater.inflate(R.layout.toast_view, null);
		   Toast toast=Toast.makeText(context,null,
					Toast.LENGTH_SHORT);
			toast.setView(toaskView);
			((TextView)toaskView.findViewById(R.id.toaskMessage)).setText(message);
			toast.show();
	  }
	
	public static void showToastView(final Context context,int message){
		   LayoutInflater inflater=LayoutInflater.from(context);
		   View toaskView=null;
		   toaskView=inflater.inflate(R.layout.toast_view, null);
		   Toast toast=Toast.makeText(context,null,
					Toast.LENGTH_SHORT);
			toast.setView(toaskView);
			((TextView)toaskView.findViewById(R.id.toaskMessage)).setText(message);
			toast.show();
	  }
}
