package com.example.studyenglish;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.bom.ImageSquare;
import com.example.bom.NavigationItem;
import com.example.bom.ParseContent;
import com.example.bom.TitleNavigationAdapter;


import structure.CallbackMatcher;
import structure.StructurePost;
import structure.Temp;
import structure.WordSave;
import utils.TestTranslate;
import utils.TestTranslate.LANGUAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.LinearLayout.LayoutParams;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PostDetail extends ActionBarActivity{
 private Thread download;
 private ImageView iconloading;
 private WebView paragraph;
 private LinearLayout linear;
 private ImageSquare clear;
 private WebView translateweb;
 private StructurePost post;
 private DataEnglish myDbHelper;
 private ArrayList<NavigationItem> navitem;
 private int zoom=20;
 private int check_stop = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_post);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.backblue);
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		navitem = new ArrayList<NavigationItem>();
		//navitem.add(new NavigationItem("zoom+", icon))
				
		
		
		post = getIntent().getExtras().getParcelable("post");
		myDbHelper = new DataEnglish(this);
		myDbHelper.openDataBase();
		iconloading = (ImageView)findViewById(R.id.loading);
		linear = (LinearLayout)findViewById(R.id.khungdich);
		clear = (ImageSquare)findViewById(R.id.x);
	    clear.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					linear.setVisibility(View.GONE);
				}
			});
	    translateweb = (WebView)findViewById(R.id.translate);
		paragraph = (WebView)findViewById(R.id.content);
		paragraph.getSettings().setJavaScriptEnabled(true);
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    zoom = appSharedPrefs.getInt("zoom",20);
		paragraph.getSettings().setDefaultFontSize(zoom);
		paragraph.requestFocus();
		paragraph.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				linear.setVisibility(View.GONE);
				return false;
			}
		});
		paragraph.setWebViewClient(new MyWebViewClient());
			 	
		
		download = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
	             
		        	StringBuilder builder = new StringBuilder(100000);
		        	DefaultHttpClient client = new DefaultHttpClient();
			        	 HttpGet httpGet = new HttpGet(post.getUrl());
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
					if(check_stop!=0) return;
					String regex_content = myDbHelper.getRegexContentOutFromCatepaper(post.getCateWeb());
					//Method method = ParseContent.class.getD("parseContent_"+regex_content, html);
					try {
						Class[] params = new Class[]{String.class};
						Object[] args = new Object[]{new String(html)};
						Class c = Class.forName("com.example.bom.ParseContent");
						Method m = c.getDeclaredMethod("parseContent_"+regex_content, params);
						Object instance = c.newInstance();
						Object r = m.invoke(instance, args);
				
					String content = r.toString();
					String resultString = filterString(content);
					post.setContent(resultString);
					

						String customHtml = "<html><head>" +
								"<meta charset='UTF-8' />" +
								"<style type=\"text/css\">a{text-decoration:none;color:#262626}</style>" +
								"</head><body><h2>"+post.getTitle()+"</h2>"+resultString+"</body></html>";
						paragraph.loadData(URLEncoder.encode(customHtml).replaceAll("\\+"," "), "text/html; charset=utf-8","UTF-8");
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
		 
	  
		   String customHtml = "<html><head><meta charset='UTF-8' /></head><body><h2>"+post.getTitle()+"</h2></body></html>";
		   paragraph.loadData(customHtml, "text/html; charset=utf-8","UTF-8");
		
		
		
		
		
		download.start();
		 
		
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
			      
				//	if (conMgr.getNetworkInfo(0).getState()==State.CONNECTED||conMgr.getNetworkInfo(1).getState()==State.CONNECTED) {
			
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
					    //notify user you are online

				//	}
				/*	else if ( conMgr.getNetworkInfo(0).getState() == State.DISCONNECTED 
					    ||  conMgr.getNetworkInfo(1).getState() == State.DISCONNECTED) {
					    //notify user you are not online
			               Toast.makeText(getApplicationContext(),"Không có kết nối mạng!", Toast.LENGTH_LONG).show();
					}	
					
			     */   
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
			check_stop = 1;
			myDbHelper.close();
		}
	
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
		    getMenuInflater().inflate(R.menu.setting,menu);
		    getMenuInflater().inflate(R.menu.post_web,menu);
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
		  case R.id.action_save:
				long check = myDbHelper.insertPost(post);
				if(check<1000){
					Toast.makeText(getApplicationContext(), "Đã chèn thành công", Toast.LENGTH_SHORT).show();
					
				}else{
					Toast.makeText(getApplicationContext(), "Bài viết này đã được lưu trước đó rồi", Toast.LENGTH_SHORT).show();
				}
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
	
}


