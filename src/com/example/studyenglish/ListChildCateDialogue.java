package com.example.studyenglish;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListChildCateDialogue extends Activity{
	ArrayList<StructureListAudioVoa> listcates_audio = new ArrayList<StructureListAudioVoa>();
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
		 	int cate = getIntent().getExtras().getInt("cate");
		 	listcates_audio = myDbHelper.getStreamFromCategory(cate);
		 myDbHelper.close();
         AdapterListAudioVoa dataAdapter = new AdapterListAudioVoa(ListChildCateDialogue.this,listcates_audio,R.drawable.dialogue); 	
 		listview.setAdapter(dataAdapter);		
 		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putParcelable("audio", listcates_audio.get(arg2));
				Intent i = new Intent(getApplicationContext(),ListenVOAActivity.class);
				i.putExtra("audio", bundle);
				startActivity(i);
			}
		});
		 
}
}
