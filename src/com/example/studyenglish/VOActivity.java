package com.example.studyenglish;

import java.io.IOException;
import java.util.ArrayList;

import com.example.bom.FragmentGrid;
import com.example.bom.FragmentListPostSaved;
import com.example.bom.ImageSquareButton;
import com.example.bom.NavigationItem;
import com.example.bom.TitleNavigationAdapter;




import structure.Newspaper;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class VOActivity extends ActionBarActivity {
	private ArrayList<Newspaper> paper = new ArrayList<Newspaper>();
	private DrawerLayout layout;
	private ArrayList<NavigationItem> navitem;
	private ActionBar actionBar;
     @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.voa);

			actionBar = getSupportActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setIcon(R.drawable.ic_action_settings);
			layout  = (DrawerLayout)findViewById(R.id.drawer_layout);
			ListView listview = (ListView)findViewById(R.id.left_drawer);
			navitem = new ArrayList<NavigationItem>();
			navitem.add(new NavigationItem("Newspapers", R.drawable.news));
			navitem.add(new NavigationItem("Post saved", R.drawable.save));
			navitem.add(new NavigationItem("Media", R.drawable.ic_action_volume_on));
			
			final Fragment fragment1 = new FragmentGrid();
			final Fragment fragment2 = new FragmentListPostSaved();
			final Fragment fragment3 = new ListCateAudio();
			listview.setAdapter(new TitleNavigationAdapter(this,navitem));
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					   layout.closeDrawers();
					switch(arg2)
					{
					 case 0 :
							FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
							ft.replace(R.id.fragment, fragment1);
							ft.commit();
							actionBar.removeAllTabs();
							actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
							break;
					 case 1 :						 
							FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
							ft1.replace(R.id.fragment, fragment2);
							ft1.commit();
							actionBar.removeAllTabs();
							actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
							break;
					 case 2:
						    if(actionBar.getNavigationMode()!=ActionBar.NAVIGATION_MODE_TABS)
						    {
						    	setNavigation();
						    }
							break;
					}
				}
			});

			
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment, fragment1);
			ft.commit();
			
		 }
    public void setNavigation()
    {
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab = actionBar.newTab()
				.setText("Audio")
				.setIcon(R.drawable.volume)
				.setTabListener(new TabListener<ListCateAudio>(this, "Audio",ListCateAudio.class));
	    actionBar.addTab(tab); 
	    tab = actionBar.newTab()
				.setText("Video")
				.setIcon(R.drawable.video)
				.setTabListener(new TabListener<ListCateVideo>(this, "Video",ListCateVideo.class));
	    actionBar.addTab(tab);
    }
    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	        //  if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.replace(R.id.fragment, mFragment, mTag);
	      //  } else {
	            // If it exists, simply attach it in order to show it
	      //      ft.attach(mFragment);
	      //  }
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	     //   if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.remove(mFragment);
	     //   }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	} 
    
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch(item.getItemId())
    	{
    	 case android.R.id.home:
    		 layout.openDrawer(Gravity.LEFT);
    		 break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Fragment frag = getSupportFragmentManager().findFragmentByTag("listaudio");
    	if(frag!=null)
    	{
    		if(frag.isVisible())
        	{
        		Fragment fragment = new ListCateAudio();
        		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    			ft.replace(R.id.fragment, fragment);
    			ft.commit();
        	}
    		Log.d("phanbom","audio khac null");
    	}
    	else
    	{
    		frag = getSupportFragmentManager().findFragmentByTag("listvideo");
        	if(frag!=null)
        	{
        		if(frag.isVisible())
            	{
            		Fragment fragment = new ListCateVideo();
            		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        			ft.replace(R.id.fragment, fragment);
        			ft.commit();
            	}
        		Log.d("phanbom","video khac null");
        	}else
        	{
        		Log.d("phanbom","video null");
        		super.onBackPressed();
        	}
    	}
    	
    }
}
