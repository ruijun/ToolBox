package com.tool.convert.rgb;

import com.tool.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * RGB转换的Activity
 */
public class ConvertRGB extends Activity {

	private Button colorBtn;
	private TextView rText,gText,bText,hexText;
	private ImageView colorImg;
	Context context;
	private ColorPickerDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.convert_rgb2);
		initViews();
		colorBtn.setOnClickListener(new ColorSelectListerner());
	}
	
	public void initViews(){
		colorBtn = (Button)findViewById(R.id.color_select);
		rText = (TextView)findViewById(R.id.r_tx);
		gText = (TextView)findViewById(R.id.g_tx);
		bText = (TextView)findViewById(R.id.b_tx);
		hexText = (TextView)findViewById(R.id.hex_tx);
		colorImg = (ImageView)findViewById(R.id.color_view);
	}
	
	class ColorSelectListerner implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog = new ColorPickerDialog(context, "颜色选择器", 
					new ColorPickerDialog.OnColorChangedListener() {
				
				@Override
				public void colorChanged(int color) {
					System.out.println("R"+Color.red(color)+" "+
							           "G"+Color.green(color)+" "+
							           "B"+Color.blue(color)+" ");
					System.out.println(RGBToHEX(Color.red(color),Color.green(color),Color.blue(color))); 
					colorImg.setBackgroundColor(color);
					rText.setText(String.valueOf(Color.red(color)));
					gText.setText(String.valueOf(Color.green(color)));
					bText.setText(String.valueOf(Color.blue(color)));
					hexText.setText(RGBToHEX(Color.red(color),Color.green(color),Color.blue(color)));
				}
			});
			dialog.show();
		}
		
	}
	
	 public String RGBToHEX(int red,int green,int blue){
	    	return '#' + toBrowserHexValue(red)+
	    			toBrowserHexValue(green) + 
	    	        toBrowserHexValue(blue); 

	    }
	    
		private String toBrowserHexValue(int number) {
			String builder = Integer.toHexString(number & 0xff);
			while (builder.length() < 2) {
				builder = "0"+builder;
			}
			return builder.toString().toUpperCase();
		}
}