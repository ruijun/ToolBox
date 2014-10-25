package com.tool.ringtone;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tool.BaseActivity;
import com.tool.MainActivity;
import com.tool.R;
import com.tool.XyCallBack;
import com.tool.common.ActivityUtils;
import com.tool.common.ToastManager;
import com.tool.menu.CustomMenu;
import com.tool.menu.MusicMenu;
import com.tool.search.phone.PhoneActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InputFile extends BaseActivity {
	
	private static MusicMenu customMenu;
	private List<InputFileListItem> directoryEntries = new ArrayList<InputFileListItem>();
	private File currentDirectory = new File("/sdcard/");

	/* 文件名，数据库 */
	MyDataBaseAdapter m_MyDatabaseHelper;
	// ListView
	ListView fileList;
	InputFileListAdapter listAdapter;
	private Button confim;
	boolean isClick = false;
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.input_file_list);
		fileList = (ListView) findViewById(R.id.listview);
		browseToRoot();
		/* 创建数据库 */
		m_MyDatabaseHelper = new MyDataBaseAdapter(this);
		m_MyDatabaseHelper.open();

		/* 确认按钮 */
		confim = (Button) findViewById(R.id.footer_button);
		confim.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// 添加铃声计数
				int count = 0;
				for (int i = 0; i < directoryEntries.size(); i++) {
					if (listAdapter.map.get(i)) {
						m_MyDatabaseHelper.insertData(directoryEntries.get(i)
								.getName(), directoryEntries.get(i).getDir());
						count++;
					}
				}
				ToastManager.showToastView(getApplicationContext(),
						Integer.toString(count) + "个铃声已添加");
				// }
			}
		});

		// 设置ListView被点击时的监听
		fileList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				String selectedFileString = directoryEntries.get(position)
						.getName();

				if (selectedFileString.equals(getString(R.string.up_one_level))) {
					// 返回上一级目录
					upOneLevel();
				} else {

					try {
						File clickedFile = null;
						Log.i("InputFile", directoryEntries.get(position).getDir());
						clickedFile = new File(directoryEntries.get(position)
								.getDir());
						if (directoryEntries.get(position).isFolder()) {
							browseTo(clickedFile);
						} else {
//							ImageView checkbox = (ImageView) v
//									.findViewById(R.id.contact_checkbox);
//							
//							if (isClick==false) {
//								
//								checkbox.setBackgroundDrawable(InputFile.this.getResources()
//										.getDrawable(R.drawable.contact_check));
//								isClick = true;
//							}else {
//								checkbox.setBackgroundDrawable(InputFile.this.getResources()
//										.getDrawable(R.drawable.contact_uncheck));
//								isClick = false;
//							}
							
//							if (checkbox.getTag()=="true") {							
//								checkbox.setBackgroundDrawable(InputFile.this.getResources()
//										.getDrawable(R.drawable.contact_uncheck));
//								checkbox.setTag("false");
//								isClick = false;
//								isClick = true;
//							}else {
//								checkbox.setBackgroundDrawable(InputFile.this.getResources()
//										.getDrawable(R.drawable.contact_check));
//								checkbox.setTag("true");
//							}
							// 设置CheckBox
							CheckBox checkBox = (CheckBox) v
									.findViewById(R.id.multiple_checkbox);
							// 点击时将选中状态反置
							checkBox.toggle();
							
							// 将选中状态加入map保存
							listAdapter.map.put(position, checkBox.isChecked());
							//listAdapter.map.put(position, true);
						}
					} catch (Exception e) {
						ActivityUtils.showDialog(InputFile.this,"确定", "提示", "没有需要保存的内容");
					}
				}

			}

		});
	}

	// 浏览SD卡的根目录
	private void browseToRoot() {
		browseTo(new File("/sdcard/"));
	}

	// 返回上一级目录
	private void upOneLevel() {
		if (this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
		// filename.clear();
	}

	// 浏览指定的目录
	private void browseTo(final File file) {
		this.setTitle(file.getAbsolutePath());
		if (file.isDirectory()) {
			this.currentDirectory = file;
			fill(file.listFiles());
		}
	}

	// 这里可以理解为设置ListActivity的源
	private void fill(File[] files) {
		// 清空列表
		this.directoryEntries.clear();

		// 如果不是根目录则添加上一级目录项
		if (this.currentDirectory.getParent() != null) {
			this.directoryEntries.add(new InputFileListItem(
					getString(R.string.up_one_level), getString(R.string.none),
					getResources().getDrawable(R.drawable.folder2), true));
		}

		Drawable AudioIcon = getResources().getDrawable(R.drawable.music);
		Drawable FolderIcon = getResources().getDrawable(R.drawable.folder2);
		for (File currentFile : files) {
			// 取得文件名
			String fileName = currentFile.getName();

			if (currentFile.isDirectory()&&!fileName.substring(0, 1).equalsIgnoreCase(".")) {
				this.directoryEntries.add(new InputFileListItem(fileName,
						currentFile.getAbsolutePath(), FolderIcon, true));
			} else if (checkEndsWithInStringArray(fileName, getResources()
					.getStringArray(R.array.fileEndingAudio))) {// 过滤掉音频文件以外的文件
				this.directoryEntries.add(new InputFileListItem(fileName,
						currentFile.getAbsolutePath(), AudioIcon, false));
			}
		}
		Collections.sort(this.directoryEntries);
		// 创建Adapter
		listAdapter = new InputFileListAdapter(this, this.directoryEntries);
		// 添加Adapter
		fileList.setAdapter(listAdapter);
	}

	// 通过文件名判断是什么类型的文件
	private boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
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

		customMenu = new MusicMenu(this, R.style.dialog,menuCallBack,2);			
		customMenu.showDialog(R.layout.menu_select_music, 0, 0);	

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
			selectAllItem();
			menuDimiss();
			break;
		case 1:
			unselectAllItem();
			menuDimiss();
			break;
		case 2:
			toggleAllItem();
			menuDimiss();
			break;
		default:
			break;
		}
	}
	
//	// 添加底部弹出菜单
//	public boolean onCreateOptionsMenu(Menu menu) {
//		super.onCreateOptionsMenu(menu);
//		menu.add(0, 0, 0, "全选");
//		menu.add(0, 1, 0, "全不选");
//		menu.add(0, 2, 0, "反选");
//
//		return true;
//	}

	
//	public boolean onOptionsItemSelected(MenuItem item) {
//		super.onOptionsItemSelected(item);
//		switch (item.getItemId()) {
//		case 0:
//			selectAllItem();
//			break;
//		case 1:
//			unselectAllItem();
//			break;
//		case 2:
//			toggleAllItem();
//			break;
//
//		}
//		return false;
//	}

	private void toggleAllItem() {
		// TODO Auto-generated method stub
		for (int i = 0; i < directoryEntries.size(); i++) {
			if (!directoryEntries.get(i).isFolder()) {
				listAdapter.map.put(i, !listAdapter.map.get(i));
			}
		}
		listAdapter.notifyDataSetChanged();// 更新Adapter
	}

	private void unselectAllItem() {
		// TODO Auto-generated method stub
		for (int i = 0; i < directoryEntries.size(); i++) {
			if (!directoryEntries.get(i).isFolder()) {
				listAdapter.map.put(i, false);
			}
		}
		listAdapter.notifyDataSetChanged();
	}

	private void selectAllItem() {
		// TODO Auto-generated method stub
		for (int i = 0; i < directoryEntries.size(); i++) {
			if (!directoryEntries.get(i).isFolder()) {
				listAdapter.map.put(i, true);
			}
		}
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event){
  		 if (keyCode == KeyEvent.KEYCODE_BACK){
  			 Intent intent = new Intent(InputFile.this, SetRingTone.class);
  			 startActivity(intent);
  			 InputFile.this.finish();
  		 }
  		 return false;
  	 }
}