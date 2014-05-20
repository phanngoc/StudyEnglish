package structure;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageSquareGrid extends ImageView{
    public ImageSquareGrid(Context context){
    	super(context);
    }
    public ImageSquareGrid(Context context,AttributeSet attrs){
    	super(context, attrs);
    }
	public ImageSquareGrid(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, 2*widthMeasureSpec/3);
	    }
}
