package com.example.studyenglish;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import utils.TestTranslate;
import utils.TestTranslate.LANGUAGE;
import utils.Util;



import com.example.bom.ImageSquare;
import com.example.bom.ImageSquareButton;
import com.example.studyenglish.ObservableWebView.OnScrollChangedCallback;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class ListenVOAActivity extends ActionBarActivity implements OnFocusChangeListener, OnTouchListener, OnBufferingUpdateListener, OnCompletionListener, OnClickListener, OnPreparedListener  {
	StructureListAudioVoa  cate;
	WebView paragraph;
	WebView translateweb;
	ImageButton playbutton;
	ImageView iconloading;
	SeekBar seekbar;
	MediaPlayer mediaPlayer;
	LinearLayout linear;
	ImageSquare clear;
	private int mediaFileLengthInMilliseconds;
	private int prepare=0;
	private int buffercomplete = 0;
	private int once = 0;
	private final Handler handler  = new Handler();
	private int zoom = 20;
      @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listen_audio_voa);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setIcon(R.drawable.backblue);
			
			//seekbar = (SeekBar)findViewById(R.id.seekbar);
			//seekbar.setMax(99);
			//seekbar.setOnTouchListener(this);
		    
			linear = (LinearLayout)findViewById(R.id.khungdich);
				    
		    translateweb = (WebView)findViewById(R.id.translate);
		    translateweb.setOnFocusChangeListener(this);
		    
		    cate = (StructureListAudioVoa)getIntent().getExtras().getBundle("audio").getParcelable("audio");
			Log.d("phanbom","cate day:"+cate.getId());
		    
		    clear = (ImageSquare)findViewById(R.id.x);
		    clear.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					linear.setVisibility(View.GONE);
				}
			});
			
		    mediaPlayer = new MediaPlayer();
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnPreparedListener(this);
			if(isOnline())
			{
				try {
					mediaPlayer.setDataSource(cate.getLink());
					mediaPlayer.prepareAsync(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			paragraph = (WebView)findViewById(R.id.content);
		    paragraph.setOnFocusChangeListener(this);
		  
			playbutton = (ImageSquareButton)findViewById(R.id.buttonplay);
			playbutton.setOnClickListener(this);
			playbutton.setBackgroundResource(R.drawable.bar_play);
			
			/*************/
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			final int width = displaymetrics.widthPixels;
			final LinearLayout bar_media = (LinearLayout)findViewById(R.id.bar_media);
			ViewTreeObserver vto = bar_media.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			    @Override
			    public void onGlobalLayout() {
			        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width-playbutton.getWidth()-30,bar_media.getHeight());
			        lp.gravity = Gravity.CENTER_VERTICAL ;
			        lp.leftMargin = 15;
			        lp.rightMargin = 15;
			        seekbar.setLayoutParams(lp);
			        
			        //ViewTreeObserver obs = bar_media.getViewTreeObserver();
			        //obs.removeOnGlobalLayoutListener(this);
			    }

			});
			    seekbar = new SeekBar(this);
			    seekbar.setId(3333);
		        seekbar.setMax(99);
			    seekbar.setOnTouchListener(this);
		        seekbar.setThumb(getResources().getDrawable(R.drawable.thumb_image));
		        seekbar.setProgress(0);
		        seekbar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_progress));
		        ((LinearLayout)findViewById(R.id.bar_media)).addView(seekbar);	
		        
			/*************/
			
			iconloading = (ImageView)findViewById(R.id.loading);
			paragraph.getSettings().setJavaScriptEnabled(true);
			SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    zoom = appSharedPrefs.getInt("zoom",20);
			paragraph.getSettings().setDefaultFontSize(zoom);
			
			String resultString = filterString(cate.getContent());
			String customHtml = "<html><head>" +
					"<meta charset='UTF-8' />" +
					"<style type=\"text/css\">a{text-decoration:none;color:#262626}</style>" +
					"</head><body><h2>"+cate.getTitle()+"</h2>"+resultString+"</body></html>";
			paragraph.loadData(URLEncoder.encode(customHtml).replaceAll("\\+"," "), "text/html; charset=utf-8","UTF-8");
     
           
			   paragraph.requestFocus();
			   paragraph.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					Log.d("phanbom","onTouch" );
					linear.setVisibility(View.GONE);
					return false;
				}
			});
			   paragraph.setWebViewClient(new MyWebViewClient());
		}
      public boolean isOnline() {
    	    ConnectivityManager cm =
    	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
    	        return true;
    	    }
    	    return false;
    	}
      private String filterString(String content)
  	{
  		
  		content = content.replaceAll("&#39;", "'");
  		content = content.replaceAll("&nbsp;", " ");
  		content = content.replaceAll("<br />", "*");
  		content = content.replaceAll("&ldquo;", "\"");
  		content = content.replaceAll("&rdquo;", "\"");
  		content = content.replaceAll("&quot;", "\"");
  		StringBuffer resultString = new StringBuffer();
  		Pattern regex_remove_html = Pattern.compile("<(\\w+)[^>]*>(.*?)</(\\w+)>");
  		Matcher regex_remove_htmlMatcher = regex_remove_html.matcher(content);
  		while (regex_remove_htmlMatcher.find()) {
  		 
  			regex_remove_htmlMatcher.appendReplacement(resultString,regex_remove_htmlMatcher.group(1));
  		}
  		regex_remove_htmlMatcher.appendTail(resultString);
  		StringBuffer result_remove_dot = new StringBuffer();
  				Pattern regex = Pattern.compile("([ ,\\.\'\"\\(\\):-\\?]*)(\\w+)([ ,\\.\'\"\\(\\):-\\?]*)");
  				Matcher regexMatcher = regex.matcher(resultString);
  				while (regexMatcher.find()) {
  				 
  				  regexMatcher.appendReplacement(result_remove_dot,regexMatcher.group(1)+"<a href='" + regexMatcher.group(2) + "'>"+regexMatcher.group(2)+"</a>"+regexMatcher.group(3));
  				}
  				regexMatcher.appendTail(result_remove_dot);
  		Log.d("phanbom","Sau khi parse:"+result_remove_dot.toString());		
  		String res = result_remove_dot.toString();
  		res = res.replaceAll("\\*", "<br />");
  		//Log.d("phanbom","Sau khi parse1:"+result_remove_dot.toString());	
  		return res;		
  	}
  	private class MyWebViewClient extends WebViewClient {
  	    @Override
  	    public boolean shouldOverrideUrlLoading(WebView view, final String url) { 
  			Log.d("phanbom","url "+url );
  			SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
  		    String select = appSharedPrefs.getString("dict_select","0");
  			if(select.equals("0"))
  			{
  				ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
  			
  						iconloading.setVisibility(View.VISIBLE);
  				        iconloading.setImageResource(R.drawable.loading);
  						Thread thread = new Thread(new Runnable() {	
  							@Override
  							public void run() {
  								// TODO Auto-generated method stub
  								TestTranslate translator = new TestTranslate();
  								Log.d("phanbom","instance translate" );
  						        translator.setSrcLang(LANGUAGE.ENGLISH);
  						        translator.setDestLang(LANGUAGE.VIETNAMESE);
  						        String data = null;
  						 
  									try {
  										data = translator.translate(url);
  									} catch (MalformedURLException e) {
  										// TODO Auto-generated catch block
  										e.printStackTrace();
  									} catch (org.apache.http.ParseException e) {
  										// TODO Auto-generated catch block
  										e.printStackTrace();
  									} catch (IOException e) {
  										// TODO Auto-generated catch block
  										e.printStackTrace();
  									}
  							    
  								Message message = handle.obtainMessage();
  								Bundle bundle = new Bundle();
  								bundle.putString("html", data);
  								message.setData(bundle);
  								handle.sendMessage(message);
  							}
  							private final Handler handle = new Handler(){
  								 @Override
  								public void handleMessage(Message msg) {
  									// TODO Auto-generated method stub
  								  String html = msg.getData().getString("html");
  								  translateweb.loadData("<meta charset='utf-8'>"+html, "text/html; charset=utf-8","UTF-8");
  								  linear.setVisibility(View.VISIBLE);
  								  translateweb.requestFocus();
  								  iconloading.setVisibility(View.GONE);
  								}
  							 };
  						});
  				        thread.start();
  			
  			}else
  			{
  				Intent intent = new Intent();
  				intent.setAction("tienlbhoc.mspdict.LookUp");
  				intent.putExtra("Word", url); //thay hello bằng từ cần tra
  				startActivity(intent);
  			}
  			
  			
  	        return true;
  	    }
  	}
  	
  	@Override
  		protected void onDestroy() {
  			// TODO Auto-generated method stub
  			super.onDestroy();
  			handler.removeCallbacksAndMessages(null);
  			mediaPlayer.stop();
  			mediaPlayer.release();
  		}
  	
  	@Override
  		public boolean onCreateOptionsMenu(Menu menu) {
  			// TODO Auto-generated method stub
  		    getMenuInflater().inflate(R.menu.setting,menu);
  		    getMenuInflater().inflate(R.menu.post_listen,menu);
  			return true;
  		}
  	@Override
  		public boolean onOptionsItemSelected(MenuItem item) {
  			// TODO Auto-generated method stub
  		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
  		Editor editor = appSharedPrefs.edit();
  		
  		 switch(item.getItemId()){
  		  case R.id.item1:
  			  Intent i = new Intent(this,Setting.class);
  			  startActivity(i); 
  			  return true;
  		  case android.R.id.home:
  			  finish();
  			  break;
  		  case R.id.action_zoom_t:
  			  paragraph.getSettings().setDefaultFontSize(++zoom);
  			  editor.putInt("zoom",zoom);
  			  editor.commit();
  			  break;
  		  case R.id.action_zoom_g:
  			  paragraph.getSettings().setDefaultFontSize(--zoom);
  			  editor.putInt("zoom",zoom);
  			  editor.commit();
  			  break;
  		 }
  			return super.onOptionsItemSelected(item);
  		}
  	
  

@Override
public void onFocusChange(View v, boolean hasFocus) {
	// TODO Auto-generated method stub
	switch(v.getId()){
	case R.id.translate:Log.d("phanbom","onFocusChange in translate:"+hasFocus );break;
	case R.id.content:
		Log.d("phanbom","onFocusChange in webkit:"+hasFocus );
		if(hasFocus)
			 linear.setVisibility(View.GONE);
	}
	
	
}


private void updateSeekbarProgress()
{	
	 seekbar.setProgress((int)((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds*100));
		  Runnable notifi = new Runnable(){
			  public void run(){
				  updateSeekbarProgress();
			  }
		  };
		  handler.postDelayed(notifi, 1000);
 }
 @Override
 public void onClick(View v){
	
	  if((v.getId()==R.id.buttonplay)&&(buffercomplete == 1)){
		  if(!mediaPlayer.isPlaying()){
			 mediaPlayer.start();
			 playbutton.setBackgroundResource(R.drawable.bar_pause);
		  }else{
			  mediaPlayer.pause();
			  playbutton.setBackgroundResource(R.drawable.bar_play);
		  }
	  }
	  
 }

 
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
}	  
@Override
public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
   if((v.getId()==seekbar.getId())&&(buffercomplete==1)){	  
		   SeekBar sb = (SeekBar)v;
		   int playPositionMilisecond = (mediaFileLengthInMilliseconds/100)*sb.getProgress();
		   mediaPlayer.seekTo(playPositionMilisecond);
   }
	  return false;
}


@Override
public void onBufferingUpdate(MediaPlayer mp, int arg1) {
	// TODO Auto-generated method stub
	if(prepare == 1){
		if((arg1 >= 10)&&(once == 0)) 
		{	
		 buffercomplete = 1;
		 once = 1;
		 mediaFileLengthInMilliseconds = (int)mp.getDuration();
		 updateSeekbarProgress();
		}
	}
    seekbar.setSecondaryProgress(arg1);	   
}




@Override
public void onCompletion(MediaPlayer mp) {
	// TODO Auto-generated method stub
	playbutton.setBackgroundResource(R.drawable.bar_play);
	mediaPlayer.seekTo(0);
	mediaPlayer.pause();
	seekbar.setProgress(0);
}




@Override
public void onPrepared(MediaPlayer arg0) {
	// TODO Auto-generated method stub
	prepare=1;
	mediaPlayer.start();
	playbutton.setBackgroundResource(R.drawable.bar_pause);
}  


}
