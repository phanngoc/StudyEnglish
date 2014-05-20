package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class TranslateVdict {
	public String translate(String word){
		Log.d("phanbom","word la"+word );
	 String webpage = Util.getContentWebpage("http://vdict.com/"+word+",1,0,0.html");
	 String result = "";
		Pattern pattern = Pattern.compile("<ul class=\"list1\">(.*?)</ul>");    
	    Matcher matcher = null;
		try {
			matcher = pattern.matcher(webpage);
		
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (matcher.find()) { 
		     result +=  matcher.group(1);
		}
	  //return Html.fromHtml(result).toString();	
	 return result;
	}
}
