package com.example.studyenglish;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class AdapterListCateDialogue extends BaseAdapter{
	  private Activity activity;
	  private ArrayList<StructureListCate> data;
	  private static LayoutInflater inflater = null;
	  public Resources res;
	  public StructureListCate listcates;
      private int intOfImage;
	  //Constructor
	   public AdapterListCateDialogue(Activity a,ArrayList<StructureListCate> d,Resources reslocal,int drawable){
		   activity = a;
		   data = d;
		   res = reslocal;
		   inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		   intOfImage = drawable;
	   }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(data.size()<=0) return 1;
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}
	    public static class ViewHolder{
	    	public TextView textview;
	    	public ImageView imageview;
	    }
		@Override
		public View getView(int position, View convertview, ViewGroup parent) {
			View vi = convertview;
			ViewHolder holder;
			if(convertview==null){
				vi = inflater.inflate(R.layout.item_list_audio_voa, null);
				holder = new ViewHolder();
				holder.textview = (TextView)vi.findViewById(R.id.textview);
				holder.imageview = (ImageView)vi.findViewById(R.id.imageview);
				
				vi.setTag(holder);
			}
			else holder = (ViewHolder)vi.getTag();
			if (data.size()<=0)
			{
				holder.textview.setText("No data");
			}else{
				
				listcates = data.get(position);
				holder.textview.setText(listcates.getTitle());
				holder.imageview.setImageResource(intOfImage);
			}
			return vi;
		}
}
