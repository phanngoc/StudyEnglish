package com.example.studyenglish;

import java.io.IOException;

import com.example.bom.CrazyActivity;
import com.example.bom.PharseActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

		public class MethodStudyActivity extends Activity implements OnClickListener{
			ImageButton voa;
			ImageButton dialogue;
			ImageButton pharse;
			ImageButton thongdung;

		   @Override
			protected void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.layout2);
				voa = (ImageButton)findViewById(R.id.studyvoa);
				voa.setOnClickListener(this);
				dialogue = (ImageButton)findViewById(R.id.dialogue);
				dialogue.setOnClickListener(this);
				pharse = (ImageButton)findViewById(R.id.pharse);
				pharse.setOnClickListener(this);
				thongdung = (ImageButton)findViewById(R.id.thongdung);
				thongdung.setOnClickListener(this);

				DataEnglish myDbHelper = new DataEnglish(this);
			    try {
			    	myDbHelper.createDataBase();
				 	} catch (IOException ioe) {
				 		throw new Error("Unable to create database");
				 	}
			    myDbHelper.close();
				
				 Typeface tf = Typeface.createFromAsset(getAssets(),
			                "fonts/Molot.otf");
			      TextView tv = (TextView) findViewById(R.id.title);
			   //   tv.setTypeface(tf);
			}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getId()){
		  	 case R.id.studyvoa:
			  Intent i = new Intent(getApplicationContext(),VOActivity.class);
			  startActivity(i);break;
		  	 case R.id.dialogue:
		  	  Intent i1 = new Intent(getApplicationContext(),DialogueActivity.class);
			  startActivity(i1);break; 
		  	 case R.id.pharse:
			  	  Intent i2 = new Intent(getApplicationContext(),PharseActivity.class);
				  startActivity(i2);break;
		  	 case R.id.thongdung:
		  		 Intent i3 = new Intent(getApplicationContext(),CrazyActivity.class);
				 startActivity(i3);break;
			}
		}
}
