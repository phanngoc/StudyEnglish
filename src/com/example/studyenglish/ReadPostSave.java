package com.example.studyenglish;

import structure.StructurePost;

import com.example.bom.FragmentGrid;
import com.example.bom.FragmentListPostSaved;
import com.example.bom.FragmentReadPostSave;
import com.example.bom.ImageSquareButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReadPostSave extends ActionBarActivity{
	private int zoom=20;
	private StructurePost post;
	private Fragment fragment1;
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_post_save);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.backblue);

		post = (StructurePost) getIntent().getBundleExtra("postin").get("post");
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    zoom = appSharedPrefs.getInt("zoom",20);
		fragment1 = new FragmentReadPostSave(post,zoom);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment, fragment1);
		ft.commit();
		
	}
   
   
   		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
   			getMenuInflater().inflate(R.menu.post_save_action,menu);
			return super.onCreateOptionsMenu(menu);
		}
   		@Override
   		public boolean onOptionsItemSelected(MenuItem item) {
   			// TODO Auto-generated method stub
   			SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
   			Editor editor = appSharedPrefs.edit();
   			
   			switch(item.getItemId())
   			{
   			 case android.R.id.home:
   			   finish();
   			   break;
   			 case R.id.action_delete:
   				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReadPostSave.this);
				alertDialogBuilder.setTitle("Are you delete post?");
				alertDialogBuilder.setMessage("Click Yes to delete.")
				.setCancelable(true)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DataEnglish myDbHelper = new DataEnglish(ReadPostSave.this);
						myDbHelper.openDataBase();
						myDbHelper.deletePost(post.getId());
						myDbHelper.close();
						AlertDialog alertDialog = new AlertDialog.Builder(
								 ReadPostSave.this).create();
								// alertDialog.setTitle("Alert Dialog Demo");
								 alertDialog.setMessage("You delete successfully");
								 alertDialog.setIcon(R.drawable.tick);
								 alertDialog.setButton("OK",
								 new DialogInterface.OnClickListener() {
								 
								    public void onClick(DialogInterface dialog, int which) {
								        finish();
								 }
								 });
								 alertDialog.show();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				break;
   			case R.id.action_zoom_t:
  			  zoom++;
  			  ((FragmentReadPostSave)fragment1).setZoom(zoom);
  			  editor.putInt("zoom",zoom);
  			  editor.commit();
  			  break;
  		    case R.id.action_zoom_g:
  		      zoom--;
    	      ((FragmentReadPostSave)fragment1).setZoom(zoom);
  			  editor.putInt("zoom",zoom);
  			  editor.commit();
  			  break;	
   			}
   			return super.onOptionsItemSelected(item);
   		}
}
