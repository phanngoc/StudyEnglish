package com.example.bom;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import structure.StructurePost;

public class ParseContent {

	public static String parseContent_1(String html)
	{
		String result = "";
		Pattern pattern = Pattern.compile("<p class=\"cnn_storypgraphtxt.*?\">(.*?)<\\/p>");    
		Matcher matcher = null;
		try {
			matcher = pattern.matcher(html);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (matcher.find()) { 
			result+=matcher.group(1);
		}	
		return result;
	}
	public static String parseContent_2(String html)
	{
		Document doc = Jsoup.parse(html);
		  Element links = doc.select("article").first();
		  Elements linkss = links.select("p.story-body-text");
		  String content = "";
		  for(Element p:linkss)
		  {
			 content += p.text();
		  }
		return content;
	}
	public static String parseContent_3(String html)
	{
		String result = "";
		Pattern pattern = Pattern.compile("<div class=\"zoomMe\">(.*?)<\\/div>");    
		Matcher matcher = null;
		try {
			matcher = pattern.matcher(html);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		while (matcher.find()) { 
			result+=matcher.group(1);
		}	
		Log.d("phanbom","ket qua"+result);
		return result;
	}
	
}
