package com.example.studyenglish;

import java.lang.reflect.Method;
import java.util.ArrayList;

import structure.StructurePost;


import com.fedorvlasov.lazylist.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class PostAdapter<E> extends BaseAdapter{
	  private Activity activity;
	  private ArrayList<E> data;
	  private static LayoutInflater inflater = null;
	  public ImageLoader imageLoader; 
	  private E listmodel = null;
	  //Constructor
	   public PostAdapter(Activity a,ArrayList<E> d,int resId){
		   activity = a;
		   data = d;
		   inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   imageLoader=new ImageLoader(a.getApplicationContext(),resId);
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
	    	public TextView title;
	    	public TextView time;
	    	public ImageView imageview;
	    }
		@Override
		public View getView(int position, View convertview, ViewGroup parent) {
			View vi = convertview;
			ViewHolder holder;
			if(convertview==null){
				vi = inflater.inflate(R.layout.item_post, null);
				holder = new ViewHolder();
				holder.title = (TextView)vi.findViewById(R.id.title);
				holder.imageview = (ImageView)vi.findViewById(R.id.imageview);
				vi.setTag(holder);
			}
			else holder = (ViewHolder)vi.getTag();
			if (data.size()<=0)
			{
				holder.title.setText("No data");
			}else{
				listmodel = data.get(position);
				Method method;
				String title="";
				String image="";
				try {
					method = listmodel.getClass().getMethod("getTitle", new Class<?>[0]);
					title = (String) method.invoke(listmodel);
					method = listmodel.getClass().getMethod("getImage", new Class<?>[0]);
					image = (String) method.invoke(listmodel);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
				holder.title.setText(title);
				imageLoader.DisplayImage(image, holder.imageview);
			}
			return vi;
		}
		public ArrayList<E> getData()
		{
			return data;
		}
}
