package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Util {
	static String convertStreamToString(InputStream in ){
		  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		  StringBuilder sb = new StringBuilder();
		  String line = null;
		  try{
			  while((line = reader.readLine())!=null){
				  sb.append(line);
			  }
			  in.close();
		  }catch(IOException e){
			  e.printStackTrace();
		  }
		
		  return sb.toString();
	  }
	public static String parse(String regex,String html){
	    String result = "";
		Pattern pattern = Pattern.compile(regex);    
         Matcher matcher = null;

		try {
			matcher = pattern.matcher(html);
		
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (matcher.find()) { 
		     result =  matcher.group(1);
		}
		return result;
	}
  
  public static String getContentWebpage(String url){
		 HttpClient client = new DefaultHttpClient();
         HttpGet request = new HttpGet(url);

        Log.d("phanbom","url la test la"+url );
          HttpResponse response_str = null;
         
          String html=null;
		try {
		    response_str = client.execute(request);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		InputStream in = null;
		try {
			in = response_str.getEntity().getContent();
		} catch (IllegalStateException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		html = convertStreamToString(in);
     return html;
	}
	
  public static InputStream getStreamWebpage(String url){
		 HttpClient client = new DefaultHttpClient();
         HttpGet request = new HttpGet(url);


       HttpResponse response_str = null;
       InputStream in = null;
       
		try {
		    response_str = client.execute(request);
		    in = response_str.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
         return in;
	}
  
	public static  InputStream getInputStreamOfUrl(String html,String regex){

		  String url_source = "";
		  url_source = parse(regex,html);
			//----------------------------------------------------------
				   HttpClient client1 = new DefaultHttpClient();
		           HttpGet request1 = new HttpGet(url_source);
		   
		           // Get the response
		         //  ResponseHandler<String> responseHandler = new BasicResponseHandler();
				   
		           HttpResponse response_str1 = null;
		          
		          
				try {
				    response_str1 = client1.execute(request1);
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				InputStream in1 = null;
				try {
					in1 = response_str1.getEntity().getContent();
				} catch (IllegalStateException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				return in1;
			//----------------------------------------------------------	
 }
	public static Bitmap getRemoteImage(final URL url){
		 try {
			final URLConnection conn  = url.openConnection();
			conn.connect();
			final BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			final Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			return bm;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	 }
	public static Bitmap getBitmapFromAsset(String strName,Activity activity)
    {
        AssetManager assetManager = activity.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}
