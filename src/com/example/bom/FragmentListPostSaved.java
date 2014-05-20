package com.example.bom;

import java.util.ArrayList;

import structure.StructurePost;

import com.example.studyenglish.DataEnglish;
import com.example.studyenglish.PostAdapter;
import com.example.studyenglish.R;
import com.example.studyenglish.ReadPostSave;

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

public class FragmentListPostSaved extends Fragment{
	private ArrayList<StructurePost> posts;
	private PostAdapter adapter;
	private ListView listview ;
  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	  Log.d("phanbom","Goi onCreateView");
	  View view = inflater.inflate(R.layout.fragment_list_post_saved,container,false);
	  DataEnglish myDbHelper = new DataEnglish(getActivity());
	 	try {
	 	   myDbHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	  posts = myDbHelper.getPostSave();
	  myDbHelper.close();
	  listview = (ListView)view.findViewById(R.id.listview);
	  adapter = new PostAdapter(getActivity(), posts,R.drawable.stub);
	  listview.setAdapter(adapter);
	  listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getActivity(),ReadPostSave.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("post", posts.get(arg2));
			i.putExtra("postin",bundle);
			startActivity(i);
		}
	  });
	  return view;
	}
  @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("phanbom","Goi resume");
		DataEnglish myDbHelper = new DataEnglish(getActivity());
	 	try {
	 	   myDbHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	  posts = myDbHelper.getPostSave();
	  myDbHelper.close();
	  adapter = new PostAdapter(getActivity(), posts,R.drawable.stub);
	  listview.setAdapter(adapter);
	}
}
