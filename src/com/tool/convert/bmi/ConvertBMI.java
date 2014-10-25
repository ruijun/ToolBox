package com.tool.convert.bmi;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tool.R;
import com.tool.common.ActivityUtils;
/*
 * BMI转换的Activity
 */
public class ConvertBMI extends Activity {
	private TextView resultText;
	private Button countButton;
	private EditText heighText;
	private RadioButton maleBtn, femaleBtn; 
	String sex = "";
	double height;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_bmi);
        //调用创建视图的函数
        creadView();
        //调用性别选择的函数
        sexChoose();
        //调用Button注册监听器的函数
        setListener();
   }
    
    //响应Button事件的函数
    private void setListener() {
    	countButton.setOnClickListener(countListner);

    }

    private OnClickListener countListner = new OnClickListener() {
		String result="";
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
			try {
        		//if ((heighText.getText().toString())!=null) 
            	Double.parseDouble(heighText.getText().toString());
            	result="你是一位"+sexChoose()+"\n"
	               +"你的身高为"+Double.parseDouble(heighText.getText().toString())+"cm"
			       +"\n你的标准体重为"+getWeight(sexChoose(), height)+"kg";
            	resultText.setText(result);				
			} catch (Exception e) {
				// TODO: handle exception
				ActivityUtils.showDialog(ConvertBMI.this, "确定", "提示", "输入的体重和性别都不能为空");
			}
		}
	};
	    
	//性别选择的函数
    private String sexChoose(){ 	
    	if (maleBtn.isChecked()) {
        	sex = "男性";
        } 
    	else if(femaleBtn.isChecked()){
        	sex = "女性";
        }
		return sex;  	
    }
    
    //创建视图的函数
    public void creadView(){
    	resultText = (TextView)findViewById(R.id.result);
    	countButton = (Button)findViewById(R.id.btn);
    	heighText = (EditText)findViewById(R.id.etx);
    	maleBtn = (RadioButton)findViewById(R.id.male);
    	femaleBtn = (RadioButton)findViewById(R.id.female); 	
    	//txt.setBackgroundResource(R.drawable.bg);
    }
    
    //标准体重格式化输出的函数
    private String format(double num) {
    	NumberFormat formatter = new DecimalFormat("0.00");
    	String str = formatter.format(num);
    	return str;
    	}
    
    //得到标准体重的函数
    private String getWeight(String sex, double height) {
    	height = Double.parseDouble(heighText.getText().toString());
	  	String weight = "";
	   	if (sex.equals("男性")) {
	   	      weight =format((height - 80) * 0.7);
	   	} 
	   	else {
	   	      weight = format((height - 70) * 0.6);
	    }
	   	return weight;
	   }
   }  
