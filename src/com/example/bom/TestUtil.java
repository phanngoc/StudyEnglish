package com.example.bom;

import utils.ListenAudio;

import com.example.studyenglish.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class TestUtil extends Activity{
	ListView list;
	TextView textview;
	ImageButton back;
	ImageButton listen;
	LinearLayout linear;
	ImageButton playbutton;
	SeekBar seekbar;
	MediaPlayer mediaPlayer;

  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.layout_crazy_day_detail);
	linear = (LinearLayout)findViewById(R.id.showlisten);
	linear.setVisibility(View.VISIBLE);
	seekbar = (SeekBar)findViewById(R.id.seekbar);
	playbutton = (ImageButton)findViewById(R.id.buttonplay);
	ListenAudio listen = new ListenAudio(getApplicationContext(), seekbar, playbutton, "http://k007.kiwi6.com/hotlink/pubxic3ugw/bai2.mp3",R.drawable.pause,R.drawable.play);
}
}
