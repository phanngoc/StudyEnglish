package com.example.bom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ImageSquareButton extends ImageButton{
	 public ImageSquareButton(Context context){
	    	super(context);
	    }
	    public ImageSquareButton(Context context,AttributeSet attrs){
	    	super(context, attrs);
	    }
		public ImageSquareButton(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
		}
		 @Override
		    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
		    }
}
