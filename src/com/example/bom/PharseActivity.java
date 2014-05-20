package com.example.bom;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.studyenglish.DataEnglish;
import com.example.studyenglish.R;

import android.app.Activity;
import android.app.ListActivity;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListAdapter;
import android.widget.TextView;

public class PharseActivity extends Activity{
	ArrayList<StructureSentences> data = new ArrayList<StructureSentences>();
	private ArrayList<HashMap<String,String>> array_sort_child;
	private ArrayList<String> array_sort_header  ;
	private ArrayList<String> dataHeader = new ArrayList<String>();
	private ArrayList<HashMap<String,String>> dataChild = new ArrayList<HashMap<String,String>>();
	int textlength=0;
	ExpandableListView expandlistview;
	ExpandableListAdapter listAdapter;
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharse);
		
		DataEnglish myDbHelper = new DataEnglish(this);
	    try {

	    	myDbHelper.createDataBase();
		 
		 	} catch (IOException ioe) {
		 
		 		throw new Error("Unable to create database");
		 
		 	}
			 	try {
			 
			 	   myDbHelper.openDataBase();
			 
			 	}catch(SQLException sqle){
			 
			 		throw sqle;
			 
			 	}
			data = myDbHelper.getSentences("pharse");
			myDbHelper.close();
			for(int i=0;i<data.size();i++){
				StructureSentences sentences = data.get(i);
				dataHeader.add(i, sentences.getContent());
				HashMap<String,String> hash = new HashMap<String, String>();
				hash.put("meaning", sentences.getMeaning());
				hash.put("example", sentences.getExample());
				dataChild.add(i,hash);
			}
			array_sort_child = new ArrayList<HashMap<String,String>>(dataChild);
			array_sort_header = new ArrayList<String>(dataHeader);
			expandlistview = (ExpandableListView)findViewById(R.id.lvExp);
			listAdapter = new ExpandableListAdapter(this, array_sort_header,array_sort_child);
			expandlistview.setAdapter(listAdapter);
			Display newDisplay = getWindowManager().getDefaultDisplay(); 
			int width = newDisplay.getWidth();
			expandlistview.setIndicatorBounds(width-40, width);
			expandlistview.setOnGroupExpandListener(new OnGroupExpandListener() {
				
				@Override
				public void onGroupExpand(int arg0) {
					// TODO Auto-generated method stub
					for(int i=0;i<array_sort_header.size();i++){
						if(i!=arg0&&expandlistview.isGroupExpanded(i))
						{
							Log.d("phanbom","Vao dc may lan" );
							expandlistview.collapseGroup(i);	
						}
						
					}
					
				}
			});
			
		final EditText search = (EditText)findViewById(R.id.search);
	    MyButton remove = (MyButton)findViewById(R.id.delete);
	    remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search.setText("");
			}
		});
		search.addTextChangedListener(new TextWatcher()
	        {
			  private int order=0;
	            public void afterTextChanged(Editable s)
	            {
	                  // Abstract Method of TextWatcher Interface.
	            }
	            public void beforeTextChanged(CharSequence s,
	                    int start, int count, int after)
	            {
	                // Abstract Method of TextWatcher Interface.
	            }
	            public void onTextChanged(CharSequence s,
	                    int start, int before, int count)
	            {
	                textlength = search.getText().length();
	                array_sort_child.clear();
	                array_sort_header.clear();
	                for (int i = 0; i < dataHeader.size(); i++)
	                {
	                    if (textlength <= dataHeader.get(i).length())
	                    {
	                        /***
	                         * If you want to highlight the countries which start with 
	                         * entered letters then choose this block. 
	                         * And comment the below If condition Block
	                         */
	                        /*if(et.getText().toString().equalsIgnoreCase(
	                                (String)
	                                listview_names[i].subSequence(0,
	                                        textlength)))
	                        {
	                            array_sort.add(listview_names[i]);
	                            image_sort.add(listview_images[i]);
	                        }*/
	                         
	                        /***
	                         * If you choose the below block then it will act like a 
	                         * Like operator in the Mysql
	                         */
	                         
	        
	                        if(dataHeader.get(i).toLowerCase().contains(search.getText().toString().toLowerCase().trim()))
	                        {
	                            array_sort_header.add(order,dataHeader.get(i));
	                            array_sort_child.add(order,dataChild.get(i));
	                            order++;
	                        }
	                      }
	                }
	                listAdapter.notifyDataSetChanged();
	                order = 0;
	               }
	            });
	         
	        
	    }
	     
	   
	   
	
	}

