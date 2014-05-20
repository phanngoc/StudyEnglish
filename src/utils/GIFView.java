package utils;

import java.io.InputStream;

import com.example.studyenglish.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GIFView extends View {
    
    private Movie mMovie;
    private float movieStart;
    private int gifId = 0;
    
    public void setGIFResource(int resId) {
        this.gifId = resId;
        initializeView();
    }
 
    public int getGIFResource() {
        return this.gifId;
    }
 

    public GIFView(Context context) {
        super(context);
        initializeView();
       
    }
 
    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }
 
    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }
 
    private void initializeView() {
            InputStream is = getContext().getResources().openRawResource(R.drawable.loader1);
            mMovie = Movie.decodeStream(is);
            movieStart = 0;
            this.invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            // Log.d("phanbom",getWidth() +"|"+ mMovie.width()+"|"+ getHeight() +"|"+ mMovie.height());
            mMovie.draw(canvas, getWidth()/2-mMovie.width()/2, getHeight()/2-mMovie.height()/2);
            this.invalidate();
        }
    }
    
    private void setAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GIFView, 0, 0);
            String gifSource = a.getString(R.styleable.GIFView_src);
            //little workaround here. Who knows better approach on how to easily get resource id - please share
            String sourceName = Uri.parse(gifSource).getLastPathSegment().replace(".gif", "");
            Log.d("phanbom","source name:"+sourceName);
            setGIFResource(getResources().getIdentifier(sourceName, "drawable", getContext().getPackageName()));
            a.recycle();
        }
    }
}
