package com.example.bom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageSquare extends ImageView{
    public ImageSquare(Context context){
    	super(context);
    }
    public ImageSquare(Context context,AttributeSet attrs){
    	super(context, attrs);
    }
	public ImageSquare(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
	    }
}
