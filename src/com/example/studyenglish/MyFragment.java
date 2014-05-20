package com.example.studyenglish;

import java.util.ArrayList;
import java.util.Random;

import structure.StructurePost;
import utils.GIFView;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MyFragment extends ListFragment {

	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	 public int idGif = 0;
	 

	 public static final MyFragment newInstance(ArrayList<StructurePost> posts)

	 {
		 Log.d("phanbom","instance myfragmnet" );
		   MyFragment f = new MyFragment();
	
		   Bundle bdl = new Bundle();
	
		   bdl.putParcelableArrayList(EXTRA_MESSAGE, posts);
	
		   f.setArguments(bdl);

	   return f;

	 }

	 public MyFragment()
	 {
		 Random rand = new Random();
		 idGif = rand.nextInt(9999);
	 }

	 @Override

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 
	   View v = inflater.inflate(R.layout.myfragment_listview, container, false);
	   GIFView gifView = new GIFView(getActivity());
	   gifView.setId(idGif);
	   FrameLayout layout = (FrameLayout)v.findViewById(R.id.wrap);
	   LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,Gravity.CENTER);
	   gifView.setLayoutParams(param);
	   layout.addView(gifView);
	   return v;

	 } 
     @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	final ArrayList<StructurePost> message = getArguments().getParcelableArrayList(EXTRA_MESSAGE);
    	PostAdapter adapter = new PostAdapter(getActivity(),message,R.drawable.stub);
 	    setListAdapter(adapter);
    }

     @Override
     public void onListItemClick(ListView l, View v, int position, long id) {
         //final ArrayList<StructurePost> message = getArguments().getParcelableArrayList(EXTRA_MESSAGE);
    	 ArrayList<StructurePost> message = ((PostAdapter)getListAdapter()).getData();
    	 if(message.size()==0)
    	 {
    		 return;
    	 }
         Intent i = new Intent(getActivity(),PostDetail.class);
			i.putExtra("post", message.get(position));
			startActivity(i);
     }
     public void removeGif()
     {
    	 View gifView = getView().findViewById(idGif);
    	 ((FrameLayout)getView().findViewById(R.id.wrap)).removeView(gifView);
     }
   }
