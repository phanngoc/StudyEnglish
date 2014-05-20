package com.example.studyenglish;



import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class ListCateVideo extends Fragment{
	
   @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	  View view = inflater.inflate(R.layout.fragment_list_post_saved, container,false);
	  DataEnglish mydb = new DataEnglish(getActivity());
	  mydb.openDataBase();
	  final ArrayList<StructureListCate> list =  mydb.getCategory("voa_video");
	  PostAdapter<StructureListCate> adapter = new PostAdapter<StructureListCate>(getActivity(),list,R.drawable.post_if_not_image);
	  mydb.close();
	  ListView listview = (ListView)view.findViewById(R.id.listview);
	  listview.setAdapter(adapter);
	  listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Fragment f1 = new ListVideoFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("cate", list.get(arg2).getId());
			f1.setArguments(bundle);
			FragmentTransaction tran1 =  getActivity().getSupportFragmentManager().beginTransaction();
			tran1.replace(R.id.fragment, f1,"listvideo");
			tran1.commit();
		}
	  });
	    
	return view;
}
}
