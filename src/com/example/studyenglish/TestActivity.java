package com.example.studyenglish;

import utils.GIFView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class TestActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GIFView gifView = new GIFView(this);
		setContentView(gifView);
	}

}
