package com.example.studyenglish;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DialogueActivity extends Activity{
	ArrayList<StructureListCate> listcates_audio = new ArrayList<StructureListCate>();
	ListView listview;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.fragment_list_post_saved);
	listview = (ListView)findViewById(R.id.listview);
	DataEnglish myDbHelper = new DataEnglish(this);
		 	try {
		 
		 	   myDbHelper.openDataBase();
		 
		 	}catch(SQLException sqle){
		 
		 		throw sqle;
		 
		 	}
		 	listcates_audio = myDbHelper.getCategory("dialogue");
		 	myDbHelper.close();
         AdapterListCateDialogue dataAdapter = new AdapterListCateDialogue(DialogueActivity.this,listcates_audio,getResources(),R.drawable.category); 	
 		listview.setAdapter(dataAdapter);		
 		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int cate = listcates_audio.get(arg2).getId();
				Intent i = new Intent(getApplicationContext(),ListChildCateDialogue.class);
				i.putExtra("cate", cate);
				startActivity(i);
			}
		});
		 
}
}
