package com.tool.search.ip;

import com.tool.BaseActivity;
import com.tool.MainActivity;
import com.tool.R;
import com.tool.common.LogManager;
import com.tool.common.SendEmail;
import com.tool.common.ToastManager;
import com.tool.search.alexa.AlexaDBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public class IpStoreList extends BaseActivity {
	private IpStoreList th;

	private ImageView homeBtn,rebackBtn;
	private TextView storeListTitle,noStoreText;
	
	private ListView list;
	private IpDBAdapter db;
	private Cursor cursor;  
	

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        th = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.look);
        init();
        loadData();
    }
    
    private void init(){
    	list = (ListView)findViewById(R.id.savingList);
    	storeListTitle = (TextView)findViewById(R.id.query_tool);
        storeListTitle.setText("收藏夹");
        noStoreText = (TextView)findViewById(R.id.no_store);
    	homeBtn = (ImageView)findViewById(R.id.home);
		rebackBtn = (ImageView)findViewById(R.id.reback);
    	list.setOnItemClickListener(click);
    	list.setOnItemLongClickListener(keyLong);
        db = new IpDBAdapter(th).open();
        homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(th, MainActivity.class);
				startActivity(intent);
				th.finish();
			}
		});
		
		rebackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(th, IpActivity.class);
				startActivity(intent);
				th.finish();
			}
		});
    }
    
    private void loadData(){
    	cursor = db.getAllTitles();
    	if (cursor.getCount()>0) {
    		list.setVisibility(View.VISIBLE);
    		noStoreText.setVisibility(View.GONE);
    		String[] columns = {IpDBAdapter.KEY_IP};
    	    int[] id = new int[] {R.id.savingNumber};
    	    SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.savingtext,cursor,columns,id);
    	    list.setAdapter(adapter);
		} else {
			list.setVisibility(View.GONE);
			noStoreText.setVisibility(View.VISIBLE);
		}
	    
    }
    
    OnItemClickListener click = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			showMobile(position);
		}};
		
	OnItemLongClickListener keyLong = new OnItemLongClickListener(){
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id){
			cursor.moveToPosition(position);
			final String ip = cursor.getString(cursor.getColumnIndex(IpDBAdapter.KEY_IP));
			String items[] = {"跳转到"+ip,"从记录中删除"};
			Builder builder = new AlertDialog.Builder(th);
			builder.setTitle(ip);
			builder.setSingleChoiceItems(items,-1,	
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							if(item == 0){
								Uri uri = Uri.parse("http://"+ip); 
								Intent it   = new Intent(Intent.ACTION_VIEW,uri); 
								startActivity(it); 
							}else if(item == 1){
								if(db.deleteTitle(cursor.getInt(cursor.getColumnIndex(IpDBAdapter.KEY_ID)))){
									ToastManager.showToastView(th, "删除成功");
									loadData();
								}else{
									ToastManager.showToastView(th, "删除失败");
								}
							}
							dialog.dismiss();
						}
					});
			builder.setPositiveButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			return true;
		}};
        
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK){
    		Intent intent = new Intent(th, IpActivity.class);
			startActivity(intent);
			th.finish();
    		db.close();
    	}
		return super.onKeyDown(keyCode, event);
	}

	private void showMobile(int position){
		   cursor.moveToPosition(position);
		   StringBuilder message = new StringBuilder("");
		   for(int num=0;num<2;num++){
			   if(num + 1 < IpDBAdapter.KEY_ALL.length){
				   message.append(cursor.getString(cursor.getColumnIndex(IpDBAdapter.KEY_ALL[num + 1]))).append("\n");
			   }
		       LogManager.d("debug", "++++++++"+cursor.getString(cursor.getColumnIndex(IpDBAdapter.KEY_ALL[num + 1])));  
		   }
		   final Dialog aboutAuthorDialog = new Dialog(th,
					R.style.dialog_style);
			//lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   aboutAuthorDialog.setContentView(R.layout.about_dialog);
			((TextView) aboutAuthorDialog.findViewById(R.id.dialog_title)).setText("详细信息");
			((TextView) aboutAuthorDialog.findViewById(R.id.dialog_message)).setText(message);
			
			((Button) aboutAuthorDialog.findViewById(R.id.dialog_button_ok))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// write your code to do things after users clicks CANCEL
							aboutAuthorDialog.dismiss();
						}
					});
			aboutAuthorDialog.show();
		
    }
}