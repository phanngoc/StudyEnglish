package com.example.studyenglish;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
  ImageButton start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DataEnglish myDbHelper = new DataEnglish(this);
	    try {
	    	myDbHelper.createDataBase();
		 	} catch (IOException ioe) {
		 		throw new Error("Unable to create database");
		 	}
	    myDbHelper.close();
		start = (ImageButton)findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			  Intent i = new Intent(getApplicationContext(),MethodStudyActivity.class);
			  overridePendingTransition(R.drawable.slider_to_left_in, R.drawable.slider_to_left_out);
			  startActivity(i); 
			}
		});
		
				
	}

	
	
}
