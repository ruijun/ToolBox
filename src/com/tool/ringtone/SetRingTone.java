package com.tool.ringtone;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tool.BaseActivity;
import com.tool.MainActivity;
import com.tool.R;
import com.tool.XyCallBack;
import com.tool.common.ActivityUtils;
import com.tool.common.ToastManager;
import com.tool.menu.CustomMenu;
import com.tool.menu.MusicMenu;
/*
 * 铃声设置的Activity
 */
public class SetRingTone extends BaseActivity {
	MusicMenu customMenu;
	private static final int INPUT = 0;
	// 试听播放器
	private MediaPlayer player = new MediaPlayer();
	/* 数据库 */
	MyDataBaseAdapter m_MyDatabaseAdapter;
	/* listview */
	private ListView mLib;
	private ImageView homeBtn,aboutBtn; 
	private TextView ringTitle;

	String prePath = "/sdcard/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_ring);
		/* 创建数据库 */
		m_MyDatabaseAdapter = new MyDataBaseAdapter(this);
		m_MyDatabaseAdapter.open();
		mLib = (ListView) findViewById(R.id.lib);
		homeBtn = (ImageView)findViewById(R.id.home);
		aboutBtn = (ImageView)findViewById(R.id.reback);
		aboutBtn.setBackgroundResource(R.drawable.about_selected);
		ringTitle = (TextView)findViewById(R.id.query_tool);
		ringTitle.setText(getResources().getString(R.string.set_ring));
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetRingTone.this, MainActivity.class);
				startActivity(intent);
				SetRingTone.this.finish();
			}
		});
		
		aboutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(SetRingTone.this, MainActivity.class);
//				startActivity(intent);
//				SetRingTone.this.finish();
//				overridePendingTransition(R.anim.zoom_enter,
//						R.anim.zoom_exit);
//				ActivityUtils.showDialog(SetRingTone.this, "确定", "关于", 
//		                 getResources().getString(R.string.about_setringtone));
				
				final Dialog aboutAuthorDialog = new Dialog(SetRingTone.this,
						R.style.dialog_style);
				//lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			   aboutAuthorDialog.setContentView(R.layout.about_dialog);
				((TextView) aboutAuthorDialog.findViewById(R.id.dialog_title)).setText("关于");
				((TextView) aboutAuthorDialog.findViewById(R.id.dialog_message))
				                              .setText(getResources().getString(R.string.about_setringtone));
				
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
		});
		UpdataAdapter();
		// ListView监听设置
		mLib.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String p = m_MyDatabaseAdapter.getPath(arg2 + 1);
				Log.i("ListView", p + " clicked");
				onStart(p);
				ToastManager.showToastView(getApplicationContext(), "长按进行设置");

			}
		});

		mLib.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// 数据库ID从1开始，ListView的Item的ID从0开始，所以加1，使数据库与ListView一一对应
				final int id = arg2 + 1;
				final String p = m_MyDatabaseAdapter.getPath(id);

				player.stop();
				// 对话框
				final CharSequence[] items = { getString(R.string.setRingtone),
						getString(R.string.setNotification),
						getString(R.string.setAlarm),
						getString(R.string.delete),
						getString(R.string.neverMind) };

				Dialog dialog = new AlertDialog.Builder(SetRingTone.this)
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									setMyRingtone(p);
									break;
								case 1:
									setMyNotification(p);
									break;
								case 2:
									setMyAlarm(p);
									break;
								case 3:
									deleteitem(id);
									break;
								case 4:
									ToastManager.showToastView(getApplicationContext(),"取消");
											
									break;
								}
							}
						}).create();
				dialog.show();
				return false;
			}
		});

		ToastManager.showToastView(this, "点击Menu键添加铃声文件");
	}

	// 返回时更新Adapter
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UpdataAdapter();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_MENU==keyCode){
			if (!menuDimiss()) {
				popMenuWindow();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	
	public boolean menuDimiss() {
		if (customMenu != null && customMenu.isShowing()) {
			customMenu.dismiss();
			return true;
		}
		return false;
	}
	
	public void popMenuWindow() {

		customMenu = new MusicMenu(this, R.style.dialog,menuCallBack,1);			
		customMenu.showDialog(R.layout.menu_music, 0, 0);	

	}
	
	XyCallBack menuCallBack = new XyCallBack() {
		@Override
		public void execute(Object... obj) {
			if (obj != null && obj.length == 1) {
				Integer pos = (Integer) obj[0];					
					clickMenu(pos);				
			}
		}
	};
	
	public void clickMenu(int pos) {
		switch (pos) {
		case 0:
			menuDimiss();
			goInputFileActivity();			
			break;	
		default:
			break;
		}
	}
	
//	// 底部弹出菜单
//	public boolean onCreateOptionsMenu(Menu menu) {
//		super.onCreateOptionsMenu(menu);
//		menu.add(0, INPUT, 0, "从SD卡导入音乐文件");
//		return true;
//	}
//
//	public boolean onOptionsItemSelected(MenuItem item) {
//		super.onOptionsItemSelected(item);
//		switch (item.getItemId()) {
//		case INPUT:
//			goInputFileActivity();
//			break;
//		}
//		return false;
//	}

	public void goInputFileActivity() {
		Intent intent = new Intent();
		intent.setClass(SetRingTone.this, InputFile.class);
		startActivity(intent);
		SetRingTone.this.finish();
	}

	/* 数据库list */
	public void UpdataAdapter() {
		// 获取数据库的Cursor
		Cursor cur = m_MyDatabaseAdapter.fetchAllData();

		if (cur != null && cur.getCount() >= 0) {
			Log.i("Database", "done");
			// ListAdapter是ListView和后台数据的桥梁
			ListAdapter adapter = new SimpleCursorAdapter(this,
			// 定义List中每一行的显示模板
			// 表示每一行包含两个数据项
					R.layout.liblist_item,
					// 数据库的Cursor对象
					cur,
					// 从数据库的TABLE_NUM和TABLE_DATA两列中取数据
					new String[] { MyDataBaseAdapter.TABLE_rNAME,
							MyDataBaseAdapter.TABLE_PATH },
					// 与NAME和PATH对应的Views
					new int[] { R.id.listitem_title, R.id.listitem_content });

			/* 将adapter添加到m_ListView中 */
			mLib.setAdapter(adapter);
		}
	}

	// 点击播放试听
	public void onStart(String p) {
		/*
		 * Uri path= Uri.parse(p); player.stop(); player =
		 * MediaPlayer.create(this, path); player.start();
		 */
		if (prePath.equals(p) && player.isPlaying()) {
			player.stop();
		} else {
			Uri path = Uri.parse(p);
			prePath = p;
			player.stop();
			player = MediaPlayer.create(this, path);
			player.start();
		}

	}

	// 设置--铃声
	public void setMyRingtone(final String p) {
		player.stop();

		File sdfile = new File(p);
		Log.i("File", p);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);

		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE, newUri);
		ToastManager.showToastView(getApplicationContext(), "电话铃声已设置");
		System.out.println("setMyRingtone()-------------");
	}

	// 设置--提示音
	public void setMyNotification(final String p) {
		player.stop();

		File sdfile = new File(p);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
		values.put(MediaStore.Audio.Media.IS_ALARM, false);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);

		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_NOTIFICATION, newUri);
		ToastManager.showToastView(getApplicationContext(),R.string.setNotificationSucceed);
				
		System.out.println("setMyNOTIFICATION-------------");
	}

	// 设置--闹铃音
	public void setMyAlarm(final String p) {
		player.stop();

		File sdfile = new File(p);
		ContentValues values = new ContentValues();
		values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
		values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
		values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
		values.put(MediaStore.Audio.Media.IS_ALARM, true);
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);

		Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
				.getAbsolutePath());
		Uri newUri = this.getContentResolver().insert(uri, values);

		RingtoneManager.setActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_ALARM, newUri);
		Toast.makeText(getApplicationContext(), R.string.setAlarmSucceed,
				Toast.LENGTH_SHORT).show();
		System.out.println("setMyNOTIFICATION-------------");
	}

	// 从列表数据库中删除铃声
	public void deleteitem(int id) {
		m_MyDatabaseAdapter.deleteData(id);
		ToastManager.showToastView(this, "铃声已从列表中移除");
		m_MyDatabaseAdapter.updateID(id);
		UpdataAdapter();

	}

	// 关闭Activity方法
	public void onDestroy() {
		super.onDestroy();
		try {
			this.finish();
		} catch (Exception e) {
		}
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event){
  		 if (keyCode == KeyEvent.KEYCODE_BACK){
  			 Intent intent = new Intent(SetRingTone.this, MainActivity.class);
  			 startActivity(intent);
  			 SetRingTone.this.finish();
  		 }
  		 return false;
  	 }
}