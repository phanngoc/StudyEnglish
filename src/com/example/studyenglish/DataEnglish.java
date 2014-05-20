package com.example.studyenglish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import structure.*;

import com.example.bom.StructureSentences;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

public class DataEnglish extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH ;
 
    private static String DB_NAME = "test.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataEnglish(Context context) {
 
    	super(context, DB_NAME, null, 2);
    	DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    	Log.d("phanbom","Link toi thu muc databases"+DB_PATH );
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
        Log.d("phanbom","Check db ton tai:"+dbExist);
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        Log.d("phanbom","Vao ham copyDataBase()" );
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
        Log.d("phanbom","Copy file from asset local" );
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME ;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
        Log.d("phanbom","open system file" );
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME ;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	public ArrayList<StructureListCate> getCategory(String cateparent){
		
       ArrayList<StructureListCate> list = new ArrayList<StructureListCate>();
	    // Select All Query
	    String selectQuery = "SELECT * FROM cate WHERE parent='"+cateparent+"'";
	   
	    
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    Log.d("phanbom","Da query bang stream"+cursor.getCount() );
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        StructureListCate cate = new StructureListCate(cursor.getInt(1), cursor.getString(2));	
	        list.add(cate);
	        } while (cursor.moveToNext());
	    }
	    
	   return list;
	}
	public ArrayList<StructureListAudioVoa> getStreamFromCategory(int cate){
		String selectQuery  = "SELECT stream._id,stream.title,stream.content,stream.link FROM stream INNER JOIN cate_stream ON cate_stream.cate_id="+cate+" AND cate_stream.stream_id=stream._id";   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    Log.d("phanbom","Da query bang stream:"+cursor.getCount() );
	    ArrayList<StructureListAudioVoa> list = new ArrayList<StructureListAudioVoa>();
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        StructureListAudioVoa cate1 = new StructureListAudioVoa(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));	
	        list.add(cate1);
	        } while (cursor.moveToNext());
	    }
	    return list;
	}
	public ArrayList<StructureSentences> getSentences(String type){
		String selectQuery = "SELECT * FROM sentences WHERE type='"+type+"'";   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    Log.d("phanbom","Da query bang stream:"+cursor.getCount() );
	    ArrayList<StructureSentences> list = new ArrayList<StructureSentences>();
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	StructureSentences cate1 = new StructureSentences(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));	
	            list.add(cate1);
	        } while (cursor.moveToNext());
	    }
	    return list;
	}
	
	public StructureListAudioVoa getAudioViaDay(String day){
		String selectQuery = "SELECT * FROM stream WHERE content='"+day+"'";   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    Log.d("phanbom","Da query bang stream:"+cursor.getCount() );

	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	StructureListAudioVoa cate1 = new StructureListAudioVoa(cursor.getInt(0),cursor.getString(2),cursor.getString(3),cursor.getString(4));	
	            return cate1;
	        } while (cursor.moveToNext());
	    }
	    return null;
	}
	public ArrayList<Newspaper> getNewspaper(){
		String selectQuery = "SELECT * FROM newspaper";   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    ArrayList<Newspaper> list = new ArrayList<Newspaper>();
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	Newspaper cate1 = new Newspaper(cursor.getString(0), cursor.getInt(1),cursor.getString(2));	
	            list.add(cate1);
	        } while (cursor.moveToNext());
	    }
	    return list;
	}
	public ArrayList<DataCatePaper> getCateFromNewspaper(int id)
	{
		String selectQuery = "select catepaper.id,cate.namecate,catepaper.regex_title_out,catepaper.regex_title_in,catepaper.regex_content_in,catepaper.link FROM cate,catepaper where catepaper.cate_id=cate._id and catepaper.newspaper_id ="+id;   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    ArrayList<DataCatePaper> list = new ArrayList<DataCatePaper>();
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	DataCatePaper datatitle = new DataCatePaper(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));	
	            list.add(datatitle);
	        } while (cursor.moveToNext());
	    }
	    return list;
	}
	public String getRegexContentOutFromCatepaper(int id)
	{
		String selectQuery = "select * from catepaper where id="+id;   
	    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	return cursor.getString(5);
	        } while (cursor.moveToNext());
	    }
	    return null;
	}
	 public long insertPost(StructurePost post)
	   {
		   String query = "SELECT * FROM postsave WHERE title = ?";
		   Cursor cursor = myDataBase.rawQuery(query, new String[]{post.getTitle()});
		   if(cursor.getCount()==0)
		   {	
			   ContentValues values = new ContentValues();
			   values.put("image", post.getImage());
			   values.put("content", post.getContent());
			   values.put("title", post.getTitle());
			   values.put("url", post.getUrl());
			   values.put("cate", post.getCateWeb());
			   SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
			   String date = df.format(Calendar.getInstance().getTime());
			   values.put("date", date);
			   long id = myDataBase.insert("postsave", null, values);
			   return id;
		   }
		   cursor.moveToFirst();
		   return cursor.getLong(cursor.getColumnIndex("id"))+1000;
	   }
	 
	 public void deletePost(int id)
	   {
		  myDataBase.delete("postsave", "id ="+id, null);
	   }
	 public ArrayList<StructurePost> getPostSave()
		{
			String selectQuery = "select * from postsave";   
		    Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		    ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
		    if (cursor.moveToFirst()) {
		        do {
		           int index_image = cursor.getColumnIndex("image");
		           int index_cate = cursor.getColumnIndex("cate");
		           int index_url = cursor.getColumnIndex("url");
		           int index_content = cursor.getColumnIndex("content");
		           int index_title = cursor.getColumnIndex("title");
		           int index_id = cursor.getColumnIndex("id");
		           StructurePost post = new StructurePost(cursor.getString(index_image),cursor.getString(index_content),cursor.getString(index_title),cursor.getString(index_url),cursor.getInt(index_cate));
		           post.setId(cursor.getInt(index_id));
		           posts.add(post);	
		        	
		        } while (cursor.moveToNext());
		    }
		    return posts;
		}
}