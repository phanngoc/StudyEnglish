package com.example.bom;

import java.util.ArrayList;

import com.example.studyenglish.R;
import com.example.studyenglish.StructureListAudioVoa;
import com.example.studyenglish.AdapterListAudioVoa.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CrazyActivity extends Activity{
	ArrayList<String> data = new ArrayList<String>();
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crazy);
		ListView list = (ListView)findViewById(R.id.expand);
		for(int i=0;i<=20;i++){
			String day = "Ngày thứ "+(i+1);
			data.add(i, day);
		}
		AdapterCrazy adapter = new AdapterCrazy(CrazyActivity.this, data, R.drawable.day);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),CrazyDayDetail.class);
				i.putExtra("day", arg2);
				startActivity(i);
			}
			
		});
		
	}
  public class AdapterCrazy  extends BaseAdapter{
		
	  private Activity activity;
	  private ArrayList<String> data;
	  private LayoutInflater inflater = null;
	  public int image_resource;
	  StructureListAudioVoa listmodel = null;
	  //Constructor
	   public AdapterCrazy(Activity a,ArrayList<String> d,int res){
		   activity = a;
		   data = d;
		   image_resource = res;
		   inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
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
	    public class ViewHolder{
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
				
				holder.textview.setText(data.get(position));
				holder.imageview.setImageResource(image_resource);
			}
			return vi;
		}
}

}
