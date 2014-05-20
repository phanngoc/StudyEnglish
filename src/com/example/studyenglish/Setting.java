package com.example.studyenglish;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Setting extends ActionBarActivity{
	private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.setting);
    	
    	ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.backblue);
    	
    	group = (RadioGroup)findViewById(R.id.radioGroup);
    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    String select = appSharedPrefs.getString("dict_select","0");
	    RadioButton button_google = (RadioButton)findViewById(R.id.google);
	    RadioButton button_mspdict = (RadioButton)findViewById(R.id.mspdict);
	    if(select.equals("0"))
	    {
	    	button_google.setChecked(true);
	    }else
	    {
	    	button_mspdict.setChecked(true);
	    }
    	
    }
    
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
    	int idCheked = group.getCheckedRadioButtonId();
		Log.d("phanbom","Co click");
		SharedPreferences appShare = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		  Editor preEditor = appShare.edit();
		switch(idCheked){
		case R.id.google:
			  preEditor.putString("dict_select","0");
			  preEditor.commit();
			  break;
		case R.id.mspdict:
			Log.d("phanbom","Vao mspdict");
			  preEditor.putString("dict_select","1");
			  preEditor.commit();
			  break;
		}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	if(item.getItemId()==android.R.id.home){
    		finish();
    	}
    	return super.onOptionsItemSelected(item);
    }
}
