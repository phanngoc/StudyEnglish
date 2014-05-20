package com.example.bom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button{
	 public MyButton(Context context) {
	        super(context);
	    }

	    public MyButton(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    // This is used to make square buttons.
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	    }
}
