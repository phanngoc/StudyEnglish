package utils;

import java.io.IOException;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class ListenAudio{
	MediaPlayer mediaPlayer;
	SeekBar seekbar;
	ImageButton playbutton;
	
	private int mediaFileLengthInMilliseconds;
	private int prepare=0;
	private int buffercomplete = 0;
	private int once = 0;
    public ListenAudio(Context context,SeekBar seekbar1,ImageButton playbutton1,String source,final int pause,final int play){
    	
    	seekbar = seekbar1;
    	playbutton = playbutton1;
    	playbutton.setBackgroundResource(play);
    	playbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(prepare==1){
					  if(!mediaPlayer.isPlaying()){
							 mediaPlayer.start();
							 playbutton.setBackgroundResource(pause);
						  }else{
							  mediaPlayer.pause();
							  playbutton.setBackgroundResource(play);
						  }
				}
			}
		});
    	
    	seekbar.setMax(99);
		seekbar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				 if(buffercomplete==1){
					   if(mediaPlayer.isPlaying()){
						   SeekBar sb = (SeekBar)arg0;
						   int playPositionMilisecond = (mediaFileLengthInMilliseconds/100)*sb.getProgress();
						   mediaPlayer.seekTo(playPositionMilisecond);
					   }
				   }
					  
					  return false;
			}
		});
    	
    	
    	
	  mediaPlayer = new MediaPlayer();
      mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				// TODO Auto-generated method stub
				Log.d("phanbom","Dang update buffer" );
				if(percent >= 10){
					if(once==0){
						buffercomplete = 1;
						once = 1;
						mediaFileLengthInMilliseconds = (int)mp.getDuration();
					} 
					 updateSeekbarProgress();
				}
					
			    seekbar.setSecondaryProgress(percent);	  
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
			}
		});
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				prepare=1;
			}
		});
		
		try {
			mediaPlayer.setDataSource(source);
			mediaPlayer.prepareAsync(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
  }
    private void updateSeekbarProgress(){
		Log.d("phanbom","init updateSeekbarProgress:"+mediaPlayer.getCurrentPosition()+"|"+mediaFileLengthInMilliseconds+"|"+(int)((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds*100) );
			 seekbar.setProgress((int)((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds*100));	 
	  }
    public void stopMedia(){
    	mediaPlayer.stop();
    }
    public void ReleaseMedia(){
    	mediaPlayer.release();
    }

}
