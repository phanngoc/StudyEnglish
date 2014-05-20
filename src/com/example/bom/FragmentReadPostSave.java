package com.example.bom;




import java.io.File;

import java.io.IOException;
import java.net.URLEncoder;





import structure.StructurePost;


import com.example.studyenglish.R;



import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentReadPostSave extends Fragment{
	private WebView paragraph;
	private WebView translateweb;
	private LinearLayout linear;
	private ImageView iconloading;
	private StructurePost post;
	private int zoom=20;
public FragmentReadPostSave(StructurePost post,int zoom)
{
	this.post = post;
	this.zoom = zoom;
}
   @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	   View view = inflater.inflate(R.layout.fragment_read_post_save, container, false);
	   iconloading = (ImageView)view.findViewById(R.id.loading);
	   linear = (LinearLayout)view.findViewById(R.id.khungdich);
	   translateweb = (WebView)view.findViewById(R.id.translate);
	   paragraph = (WebView)view.findViewById(R.id.content);
	   String result =  "<html><head>" +
		"<meta charset='UTF-8' />" +
		"<style type=\"text/css\">a{text-decoration:none;color:#262626}</style>" +
		"</head><body><h2>"+post.getTitle()+"</h2>"+post.getContent()+"</body></html>";
	    paragraph.loadData(URLEncoder.encode(result).replaceAll("\\+"," "), "text/html; charset=utf-8","UTF-8");
		paragraph.getSettings().setJavaScriptEnabled(true);
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
	return view;
}
   private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, final String url) { 
			Log.d("phanbom","url "+url );
//				iconloading.setVisibility(View.VISIBLE);
		        iconloading.setImageResource(R.drawable.loading);
				Thread thread = new Thread(new Runnable() {	
					@Override
					public void run() {
						// TODO Auto-generated method stub
					

						
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
		        //  thread.start();
		        
				Intent intent = new Intent();
				intent.setAction("tienlbhoc.mspdict.LookUp");
				intent.putExtra("Word", url); //thay hello bằng từ cần tra
				startActivity(intent);
				
	        return true;
	    }
	}
   public void setZoom(int zoom)
   {
	   this.zoom = zoom;
	   paragraph.getSettings().setDefaultFontSize(zoom);
   }

}
