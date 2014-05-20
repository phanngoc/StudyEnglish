
package com.example.studyenglish;



import android.app.Activity;
import android.app.ActivityOptions;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;



public class WatchVideoVOA extends Activity {
	VideoView videoview;
	MediaController mediacontroller;
  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                             WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.play_video_voa);
		 
		StructureListAudioVoa cate = (StructureListAudioVoa)getIntent().getExtras().getBundle("audio").getParcelable("audio");
		videoview = (VideoView)findViewById(R.id.videoview);
		  mediacontroller = new MediaController(WatchVideoVOA.this);
		  mediacontroller.setAnchorView(videoview);

		  videoview.setMediaController(mediacontroller);

		  videoview.setVideoURI(Uri.parse(cate.getLink()));
		  videoview.requestFocus();
		  videoview.start();  
		  
	}
}
