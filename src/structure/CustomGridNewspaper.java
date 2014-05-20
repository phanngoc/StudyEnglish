package structure;

import java.util.ArrayList;

import utils.Util;

import com.example.studyenglish.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridNewspaper extends ArrayAdapter<Newspaper> {
	 Context context;
	 int layoutResourceId;
	 ArrayList<Newspaper> data = new ArrayList<Newspaper>();

	 public CustomGridNewspaper(Context context, int layoutResourceId, ArrayList<Newspaper> data) {
	  super(context, layoutResourceId, data);
	  this.layoutResourceId = layoutResourceId;
	  this.context = context;
	  this.data = data;
	 }

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	  View row = convertView;
	  RecordHolder holder = null;

	  if (row == null) {
	   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	   row = inflater.inflate(layoutResourceId, parent, false);

	   holder = new RecordHolder();
	  // holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
	   holder.imageItem = (ImageSquareGrid) row.findViewById(R.id.item_image);
	   row.setTag(holder);
	  } else {
	   holder = (RecordHolder) row.getTag();
	  }

	  Newspaper item = data.get(position);
	 // holder.txtTitle.setText(item.getString());
	  Bitmap bitmap = Util.getBitmapFromAsset(item.getLogo(),(Activity) context);
	  int length = parent.getWidth()/2-10;
	  
	  Bitmap resized = Bitmap.createScaledBitmap(bitmap,length,length*2/3, true);
	  holder.imageItem.setImageBitmap(resized);
	  return row;

	 }

	 static class RecordHolder {
	  TextView txtTitle;
	  ImageView imageItem;
	 }
}	 
