package com.example.bom;

import java.util.ArrayList;

import structure.CustomGridNewspaper;
import structure.Newspaper;

import com.example.studyenglish.DataEnglish;
import com.example.studyenglish.R;
import com.example.studyenglish.ReadPaper;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentGrid extends Fragment{
	private ArrayList<Newspaper> paper = new ArrayList<Newspaper>();
	private GridView gridview;
  @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	  View view = inflater.inflate(R.layout.fragment_grid_newspaper, container, false);
	  gridview = (GridView)view.findViewById(R.id.gridview1);
		DataEnglish myDbHelper = new DataEnglish(getActivity());
			 	try {
			 
			 	   myDbHelper.openDataBase();
			 
			 	}catch(SQLException sqle){
			 
			 		throw sqle;
			 
			 	}
	     paper = myDbHelper.getNewspaper();
		 ArrayAdapter<Newspaper> adapter = new CustomGridNewspaper(getActivity(), R.layout.grid_custom_newspaper, paper);
		 gridview.setAdapter(adapter);
		 gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),ReadPaper.class);
				intent.putExtra("id",arg2);
				startActivity(intent);
			}
			 
		 });
	 	 myDbHelper.close();
	return view;
}
  @Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	
	
}
}
