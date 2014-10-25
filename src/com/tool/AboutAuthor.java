package com.tool;

import android.os.Bundle;
import android.view.Window;

public class AboutAuthor extends BaseActivity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
	}
}
