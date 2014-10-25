package com.tool.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tool.BaseActivity;
import com.tool.MainActivity;
import com.tool.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SendEmail extends BaseActivity {
	private TextView sendEmailTitle;
	private Button sendBtn;
	private EditText recipientEdit;
	private EditText titleEdit;
	private EditText feedbackEdit;
	private ImageView homeBtn,rebackBtn;
	
	private String[] strEmailReciver;
	private String strEmailSubject;
	private String strEmailBody;
	
	private static final int PICK_CONTACT_SUBACTIVITY = 2;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_email);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); 
        
        sendEmailTitle = (TextView)findViewById(R.id.query_tool);
        sendEmailTitle.setText("发送邮件");
        sendBtn=(Button)findViewById(R.id.send_btn);
        recipientEdit=(EditText)findViewById(R.id.recipient_edit);
        titleEdit=(EditText)findViewById(R.id.title_edit);
        feedbackEdit=(EditText)findViewById(R.id.feedback_edit);
        homeBtn = (ImageView)findViewById(R.id.home);
		rebackBtn = (ImageView)findViewById(R.id.reback);
		
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendEmail.this, MainActivity.class);
				startActivity(intent);
				SendEmail.this.finish();
			}
		});
		
		rebackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendEmail.this, MainActivity.class);
				startActivity(intent);
				SendEmail.this.finish();
			}
		});
        sendBtn.setEnabled(false);
               
        recipientEdit.setOnLongClickListener(searhEmail);
        
        sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 
				Intent mailIntent=new Intent(android.content.Intent.ACTION_SEND);
				
				mailIntent.setType("plain/test");
				strEmailReciver=new String[]{ recipientEdit.getText().toString() };
				
				strEmailSubject=titleEdit.getText().toString();
				
				strEmailBody=feedbackEdit.getText().toString();
				mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, strEmailReciver);
				
				mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strEmailSubject);
				
				mailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strEmailBody);
				
			    startActivity(Intent.createChooser(mailIntent,"发送邮件"));
				
			
			
			/*  第二种方法
			Uri uri=Uri.parse("mailto:terryyhl@gmail.com");
			Intent MymailIntent=new Intent(Intent.ACTION_SEND,uri);
			startActivity(MymailIntent);
			
			*/
			
			/* 第三种方法
			Intent testintent=new Intent(Intent.ACTION_SEND);
			String[] tos={"terryyhl@gmail.com"};
			String[] ccs={"kalaicheng@hotmail.com"};
			testintent.putExtra(Intent.EXTRA_EMAIL, tos);
			testintent.putExtra(Intent.EXTRA_CC, ccs);
			testintent.putExtra(Intent.EXTRA_TEXT, "这是内容");
			testintent.putExtra(Intent.EXTRA_SUBJECT, "这是标题"); 
			testintent.setType("message/rfc822");
			startActivity(Intent.createChooser(testintent, "发送"));
			
			*/
			
			
			/*
			 /*第四种方法传附件
			 Intent testN=new Intent(Intent.ACTION_SEND);
			  testN.putExtra(Intent.EXTRA_SUBJECT, "标题");
			  testN.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/music.mp3");
			  startActivity(Intent.createChooser(testN, "发送"));
			 */
			 
			}
		});
        
         
        recipientEdit.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(isEmail(recipientEdit.getText().toString()))
				{
					 sendBtn.setEnabled(true);
				}
				else
				{
					 sendBtn.setEnabled(false);
				}
				
				return false;
			}
		});
        
        
    }
    // 检查Email格式
    public static boolean isEmail(String strEmail) {
     String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

     Pattern p = Pattern.compile(strPattern);
     Matcher m = p.matcher(strEmail);
     return m.matches();
    }


    private OnLongClickListener searhEmail=new OnLongClickListener(){
    	 public boolean onLongClick(View arg0) {
    		 Uri uri=Uri.parse("content://contacts/people");
    		 Intent intent=new Intent(Intent.ACTION_PICK,uri);
    		 startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
			 return false;
			}
    	 ;
    };
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 
    	switch (requestCode) {
		case PICK_CONTACT_SUBACTIVITY:
			final Uri uriRet=data.getData();
			if(uriRet!=null)
			{
				try {
					Cursor c=managedQuery(uriRet, null, null, null, null);
					c.moveToFirst();
					//取得联系人的姓名 
					String strName=c.getString(c.getColumnIndexOrThrow(People.NAME));
					//取得联系人的EMAIL
					String[] PROJECTION=new String[]{
							Contacts.ContactMethods._ID,
							Contacts.ContactMethods.KIND,
							Contacts.ContactMethods.DATA
					};
					//查询指定人的Email
					 Cursor newcur=managedQuery(
							 Contacts.ContactMethods.CONTENT_URI,
							 PROJECTION, 
							 Contacts.ContactMethods.PERSON_ID+"=\'"
							 +c.getLong(c.getColumnIndex(People._ID))+"\'", 
							 null, null);
					startManagingCursor(newcur);
					String email="";
					if(newcur.moveToFirst())
					{
						email=newcur.getString(newcur.getColumnIndex
								(Contacts.ContactMethods.DATA));
						recipientEdit.setText(email);
					}
					 
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(SendEmail.this, e.toString(), 1000).show();
				}
			}
			break;

		default:
			break;
		}
    	super.onActivityResult(requestCode, resultCode, data);
    };  
}