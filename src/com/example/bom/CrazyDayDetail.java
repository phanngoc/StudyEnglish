package com.example.bom;

import java.util.ArrayList;

import utils.ListenAudio;

import com.example.studyenglish.DataEnglish;
import com.example.studyenglish.R;
import com.example.studyenglish.StructureListAudioVoa;
import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class CrazyDayDetail extends Activity {
	ListView list;
	TextView textview;
	ImageButton back;
	ImageButton listen;
	LinearLayout linear;
	ImageButton playbutton;
	SeekBar seekbar;
	ArrayList<StructureSentences> listsentences = new ArrayList<StructureSentences>();
	ArrayList<StructureSentences>  listdataAdapter = new ArrayList<StructureSentences>();
	private int day,inc=0;
	ListenAudio listenaudio;
	private final Handler handler  = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		day = getIntent().getExtras().getInt("day")+1;
		setContentView(R.layout.layout_crazy_day_detail);
		list = (ListView)findViewById(R.id.listview);
		textview = (TextView)findViewById(R.id.day);
		textview.setText("Ngày thứ "+(day+1));
		back = (ImageButton)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		linear = (LinearLayout)findViewById(R.id.showlisten);
		listen = (ImageButton)findViewById(R.id.listen);
		listen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(linear.getVisibility()==View.GONE){
					linear.setVisibility(View.VISIBLE);
				}else{
					linear.setVisibility(View.GONE);
				}		
			}
		});
		seekbar = (SeekBar)findViewById(R.id.seekbar);
		playbutton = (ImageButton)findViewById(R.id.buttonplay);
		
		
		DataEnglish myDbHelper = new DataEnglish(this);
	     
	 	try {	 
	 	   myDbHelper.openDataBase(); 
	 	}catch(SQLException sqle){
	 		throw sqle; 
	 	}
	    StructureListAudioVoa listCrazy = myDbHelper.getAudioViaDay("bai"+day);	
	    listenaudio = new ListenAudio(getApplicationContext(), seekbar, playbutton, "http://k007.kiwi6.com/hotlink/"+listCrazy.getLink()+"/"+listCrazy.getContent()+".mp3",R.drawable.pause,R.drawable.play);
		 
		 	
		 listsentences = myDbHelper.getSentences("crazyenglish");
		myDbHelper.close();
		for(int i=0;i<listsentences.size();i++){
			Log.d("phanbom","listsentences may lan:"+i+"|"+listsentences.get(i).getExample());
			//Log.d("phanbom","check:"+day+"|"+listsentences.get(i).getExample()+listsentences.get(i).getExample().equals(day+""));
			if(listsentences.get(i).getExample().equals(day+"")){
				listdataAdapter.add(listsentences.get(i));
				inc++;
				//Log.d("phanbom","loc duoc "+inc );
			}
		}
		AdapterCrazyDetail adapter = new AdapterCrazyDetail(CrazyDayDetail.this, listdataAdapter, R.drawable.back);
		list.setAdapter(adapter);
	}
	  public class AdapterCrazyDetail  extends BaseAdapter{
			
		  private Activity activity;
		  private ArrayList<StructureSentences> data;
		  private LayoutInflater inflater = null;
		  public int image_resource;
		  StructureListAudioVoa listmodel = null;
		  //Constructor
		   public AdapterCrazyDetail(Activity a,ArrayList<StructureSentences> d,int res){
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
		    	public TextView sen;
		    	public TextView mea;
		    }
			@Override
			public View getView(int position, View convertview, ViewGroup parent) {
				View vi = convertview;
				ViewHolder holder;
				if(convertview==null){
					vi = inflater.inflate(R.layout.item_crazy_day_detail, null);
					holder = new ViewHolder();
					holder.sen = (TextView)vi.findViewById(R.id.sentences);
					holder.mea = (TextView)vi.findViewById(R.id.meaning);
					
					vi.setTag(holder);
				}
				else holder = (ViewHolder)vi.getTag();
				if (data.size()<=0)
				{
					holder.sen.setText("No data");
				}else{
					
					holder.sen.setText(data.get(position).getContent());
					holder.mea.setText(data.get(position).getMeaning());
				}
				return vi;
			}
	}
	  @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		listenaudio.ReleaseMedia();
	}
	
}
