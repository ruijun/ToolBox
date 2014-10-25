package com.tool.menu;

import com.tool.MainActivity;
import com.tool.R;
import com.tool.XyCallBack;
import com.tool.common.BadgeView;
import com.tool.common.LogManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MusicMenu extends Dialog{
	private Cursor cursor;
	TextView storeText;
	private Context context;
	private Window window = null;
	private int screenWidth,screenHeight;
	private int whichInterface=-1;
	public LinearLayout layout;//整个菜单控件
	XyCallBack callBack;
	private LinearLayout layout_menu_delete;//删除数据
	private LinearLayout layout_menu_backup;//备份数据
	public  LinearLayout layout_menu_store;//添加收藏
	private LinearLayout layout_menu_check;//查看收藏
	
	private LinearLayout layout_menu_select;//全选
	private LinearLayout layout_menu_unselect;//全不选
	private LinearLayout layout_menu_toggle;//反选
	
	public MusicMenu(Context context){
		 super(context);
	        // TODO Auto-generated constructor stub
	     this.context = context;
	}
	
	
	public MusicMenu(Context context, int theme,XyCallBack callBack,int whichInterface) {
		super(context, theme);
	    this.context =context;
	    this.callBack = callBack;
	    this.whichInterface=whichInterface;
	}


	public void showDialog(int layoutResID, int x, int y){

        setContentView(layoutResID);
        windowDeploy(x, y);
        show();
    }
	public void windowDeploy(int x, int y){
        window = getWindow(); //得到对话框
        WindowManager.LayoutParams wl = window.getAttributes();
        //定义DisplayMetrics对象 
        DisplayMetrics dm = new DisplayMetrics();
        //获取窗口属性 
        window.getWindowManager().getDefaultDisplay().getMetrics(dm); 
        //窗口宽度 
        screenWidth = dm.widthPixels; 
        //窗口高度 
        screenHeight = dm.heightPixels; 
        wl.width=screenWidth;
        wl.height=screenHeight;
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移  
        window.setAttributes(wl);
        window.setWindowAnimations(R.anim.menu_enter_anim);
        
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
		initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		switch (whichInterface) {
		case 0:
			addListener(layout_menu_delete, 0);
			addListener(layout_menu_backup, 1);
			addListener(layout_menu_store, 2);
			addListener(layout_menu_check, 3);
			break;
		case 1:
			addListener(layout, 0);
			break;
		case 2:
			addListener(layout_menu_select, 0);
			addListener(layout_menu_unselect, 1);
			addListener(layout_menu_toggle, 2);
			break;
		default:
			break;
		}
		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		switch (whichInterface) {
		case 0:
			layout= (LinearLayout) findViewById(R.id.layout_menu_query);
			layout_menu_delete = (LinearLayout)findViewById(R.id.layout_menu_delete);
			layout_menu_backup = (LinearLayout)findViewById(R.id.layout_menu_backup);
			layout_menu_store = (LinearLayout)findViewById(R.id.layout_menu_store);
			layout_menu_check = (LinearLayout)findViewById(R.id.layout_menu_check);
			break;
		case 1:
			layout = (LinearLayout)findViewById(R.id.layout_menu_music);
			break;
		case 2:
			layout = (LinearLayout)findViewById(R.id.layout_music_select);
			layout_menu_select = (LinearLayout)findViewById(R.id.layout_menu_select);
			layout_menu_unselect = (LinearLayout)findViewById(R.id.layout_menu_unselect);
			layout_menu_toggle = (LinearLayout)findViewById(R.id.layout_menu_toggle);
			break;
		default:
			break;
		}
		
	}	
	protected void setImg(View v,boolean bl){
//		if (bl==true) {
//			v.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gd_quick_action_grid_selector_pressed2));
//		} else {
//			v.setBackgroundColor(R.color.transparent);
//		}
		
	}
	protected void addListener(final View result,final int pos){
		
		result.setOnTouchListener(new OnTouchListener(){
			
			  public boolean onTouch(View v, MotionEvent event) {
				  if(event.getAction() ==MotionEvent.ACTION_DOWN){
					 setImg(v,true);
					 LogManager.d("debug", "0000000000"); 
					  
 				 }else if(event.getAction() == MotionEvent.ACTION_UP){
 					setImg(v,false);
 					LogManager.d("debug", "1111111111"); 
 					if(callBack != null){
 					  callBack.execute(pos);
 					}
					 
				 }else if(event.getAction() == MotionEvent.ACTION_OUTSIDE || event.getAction() == MotionEvent.ACTION_CANCEL){
					 setImg(v,false);
					 LogManager.d("debug", "22222222222"); 
				 } 
				  return true;
			}
	 	});
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 
		int height = layout.getHeight();
		if(event.getY() < screenHeight-height){
			closeMenu();
		}
		
		return super.onTouchEvent(event);
	}
	
	protected void closeMenu(){
		this.dismiss();
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(KeyEvent.KEYCODE_MENU == keyCode){
			closeMenu();
		}
		return super.onKeyUp(keyCode, event);
	}
}
