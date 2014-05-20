package com.example.studyenglish;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import structure.*;

import utils.Util;




import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.location.GpsStatus.Listener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReadPaper extends ActionBarActivity{
  private String host="10.0.3.2";	

  private ArrayList<DataCatePaper> datacate;

  private HorizontialListView listview;
  private  List<MyFragment> fList;
  private MyPageAdapter mypage;
  private ViewPager pager;
  private View save = null;
  private int current_cate=0;
  private int[] check_ar_title;
  private MyAdapter adapter ;
  private int init = 0;
  
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.read_website);
	ActionBar actionBar = getSupportActionBar();
	actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setDisplayUseLogoEnabled(false);
	actionBar.setIcon(R.drawable.backblue);
	
	DataEnglish myDbHelper = new DataEnglish(this);
		 	try {
		 
		 	   myDbHelper.openDataBase();
		 
		 	}catch(SQLException sqle){
		 		throw sqle;
		 	}
	 int id = getIntent().getExtras().getInt("id")+1;	 	
	 datacate = myDbHelper.getCateFromNewspaper(id) ;
	 adapter = new MyAdapter();
	 fList = new ArrayList<MyFragment>(datacate.size());
	 check_ar_title = new int[datacate.size()];
	 check_ar_title[0] = 1;
	 for(int i=1;i<check_ar_title.length;i++)
	 {
		 check_ar_title[i] = 0;
	 }
	 myDbHelper.close();
     pager = (ViewPager)findViewById(R.id.viewpager);
     pager.setOffscreenPageLimit(7);
     pager.setOnPageChangeListener(new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			if(save==null) Log.d("phanbom","null rui" );
			//Log.d("phanbom","size of listview:"+listview.getFirstVisiblePosition()+"|"+arg0+"|"+datacate.size());
			//View current_title = listview.getChildAt(arg0-listview.getFirstVisiblePosition());
			ArrayList<View> save_view = adapter.getSaveView();
			int dscroll = 0;
		
			for(int i=0;i<save_view.size();i++)
			{
				if(i<arg0)
					dscroll+=save_view.get(i).getWidth();
			}
			
			Log.d("phanbom","dscroll: "+dscroll);
			if(arg0==0)
			{
				listview.scrollTo(dscroll);
			}else
			{
				listview.scrollTo(dscroll-save_view.get(arg0-1).getWidth()/2);
			}	
			
			
			for(int i=0;i<save_view.size();i++)
			{
				if(save_view.get(i)!=null)
				{
					save_view.get(i).setBackgroundColor(getResources().getColor(R.color.item_scroll)); 
				}
			}
		//	if(current_title!=save&&save!=null)
		//	{
		//		current_title.setBackgroundColor(getResources().getColor(R.color.item_scroll));
			//	save.setBackgroundResource(R.color.title);
		//	}
			View current_title = save_view.get(arg0);
			current_title.setBackgroundColor(getResources().getColor(R.color.title_highlight));
			save = current_title;
		
			if(check_ar_title[arg0] == 0)
			 {
				check_ar_title[arg0]=1;
				current_cate = arg0;
				load_pager(arg0);
			 }
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	});
   
     
    listview = (HorizontialListView) findViewById(R.id.listitle); 
    listview.setAdapter(adapter);
    listview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
				if(check_ar_title[arg2] == 0)
				 {
					load_pager(arg2);
					check_ar_title[arg2]=1;
					current_cate = arg2;
				 }
			 
			pager.setCurrentItem(arg2);
			if(save==null) Log.d("phanbom","null rui" );
			arg1.setBackgroundColor(getResources().getColor(R.color.title_highlight));
			if(arg1!=save)
			{
				save.setBackgroundColor(getResources().getColor(R.color.item_scroll));
			}
			save = arg1;	
		}
	});
    for(int i=0;i<datacate.size();i++)
    {
    	ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
    	fList.add(MyFragment.newInstance(posts));
    }
    load_pager(0);

	mypage = new MyPageAdapter(getSupportFragmentManager(), fList);
	pager.setAdapter(mypage);
	
 }
 private void load_pager(final int cate)
 {
	 Thread download = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
	             
		        	StringBuilder builder = new StringBuilder(100000);
		        	DefaultHttpClient client = new DefaultHttpClient();
			        	 HttpGet httpGet = new HttpGet(datacate.get(cate).getLink());
			 	        try {
			 	            HttpResponse execute = client.execute(httpGet);
			 	            InputStream content = execute.getEntity().getContent();
			 	
			 	            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
			 	            String s = "";
			 	            while ((s = buffer.readLine()) != null) {
			 	                builder.append(s);
			 	            }
			 	        } catch (Exception e) {
			 	            e.printStackTrace();
			 	        }
			 	        Message message = handle.obtainMessage();
			 			Bundle bund = new Bundle();
			 			bund.putString("html",builder.toString()); 
			 			message.setData(bund);
			 			handle.sendMessage(message); 

			}
			
			 private final Handler handle = new Handler(){
				 @Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					String html = msg.getData().getString("html");
				    //ArrayList<StructurePost> posts = parse(datacate.get(current_cate).getRegexTitleOut(),html);
					try {
						Class[] params = new Class[]{String.class,int.class,String.class};
						Object[] args = new Object[]{new String(html),datacate.get(cate).getId(),datacate.get(cate).getLink()};
						Class c = Class.forName("com.example.bom.JsoPar");
						Method m = c.getDeclaredMethod("parse_title_out_"+datacate.get(cate).getRegexTitleOut().trim(), params);
						Object instance = c.newInstance();
						Object r = m.invoke(instance, args);
				    ArrayList<StructurePost> posts = (ArrayList<StructurePost>)r;

					
				    String name = mypage.makeFragmentName(R.id.viewpager, cate);
			        Fragment f = getSupportFragmentManager().findFragmentByTag(name);
			        if(f==null)
			        {
			        	Log.d("phanbom","bi null rui");
			        	check_ar_title[cate]=0;
			        	return;
			        }
			        	
			        PostAdapter adapter = new PostAdapter(ReadPaper.this,posts,R.drawable.stub);
				    //((ListFragment)mypage.getItem(current_cate)).setListAdapter(adapter);
			        ((MyFragment)f).removeGif();
			        ((MyFragment)f).setListAdapter(adapter);
			        
			        
					mypage.notifyDataSetChanged();
					pager.setCurrentItem(cate);
					Log.d("phanbom","MyPageAdapter"+mypage.getCount() );
					Log.d("phanbom","viewpager"+pager.getChildCount() );
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			 };
		});
	 download.start();
 }

  @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	   switch(item.getItemId())
	   {
	   	case android.R.id.home:
	   		finish();
	   		break;
	   }
		return super.onOptionsItemSelected(item);
	}
 static class MyPageAdapter extends FragmentPagerAdapter {

	  private List<MyFragment> fragments;


	  public MyPageAdapter(FragmentManager fm, List<MyFragment> fragments) {

	    super(fm);

	    this.fragments = fragments;

	  }

	  @Override 

	  public Fragment getItem(int position) {

	    return this.fragments.get(position);

	  }


	  @Override

	  public int getCount() {

	    return this.fragments.size();

	  }
	  private static String makeFragmentName(int viewId, int index) {
	        return "android:switcher:" + viewId + ":" + index;
	  }
	}
 
 
 class MyAdapter extends BaseAdapter{
	private ArrayList<View> save_view = new ArrayList<View>();
	MyAdapter()
	{
		Log.d("phanbom","data case size:"+datacate.size());
		for(int i=0;i<datacate.size();i++)
		{
			save_view.add(null);
		}
	}
	@Override 
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View retval = LayoutInflater.from(arg2.getContext()).inflate(R.layout.view_title_item, null);
		TextView textview = (TextView)retval.findViewById(R.id.text_only);
		textview.setText(datacate.get(arg0).getName());
		if(arg0==0 && init==0) 
			{
			 init = 1;
			 save = retval;
			 save.setBackgroundColor(getResources().getColor(R.color.title_highlight));
			}
		Log.d("phanbom","getview:"+arg0);
		//if(save_view)
		save_view.set(arg0, retval);
		return retval;
	}
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return save_view.get(arg0);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datacate.size();
	}
	public ArrayList<View> getSaveView()
	{
		return save_view;
	}
};


}
