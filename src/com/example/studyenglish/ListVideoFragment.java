package com.example.studyenglish;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListVideoFragment extends Fragment{
	ArrayList<StructureListAudioVoa> list = new ArrayList<StructureListAudioVoa>();
	ListView listview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_list_post_saved,container,false);
		listview = (ListView)view.findViewById(R.id.listview);
		int cate = getArguments().getInt("cate");
	    Log.d("phanbom","cate trong listautoactivity"+cate );
	    DataEnglish myDbHelper = new DataEnglish(getActivity());
     
			 	try {	 
			 	   myDbHelper.openDataBase(); 
			 	}catch(SQLException sqle){
			 		throw sqle; 
			 	}
			list = myDbHelper.getStreamFromCategory(cate);
			myDbHelper.close();
			PostAdapter<StructureListAudioVoa> adapter = new PostAdapter<StructureListAudioVoa>(getActivity(), list,R.drawable.volume);
			listview.setAdapter(adapter);
			   
	  if(list.size()!=0){
		   listview.setOnItemClickListener(new OnItemClickListener() {
 
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putParcelable("audio", list.get(arg2));
					Intent i = new Intent(getActivity(),WatchVideoVOA.class);
					i.putExtra("audio", bundle);
					startActivity(i);
				}
		   	});
	  }

		return view;
	} 
   
}
