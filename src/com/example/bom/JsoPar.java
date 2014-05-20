package com.example.bom;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import structure.StructurePost;

public class JsoPar {
	
	  public static ArrayList<StructurePost> parse_title_out_1(String html,int cate_web,String link)
	  {
		  ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
		  Document doc = Jsoup.parse(html);
		  Element links = doc.select("ul.cnn_bulletbin").first();
		  Elements linkss = links.select("li");
		  for(Element ul:linkss)
		  {
			  
			  String url = ul.select("a").attr("href");
			  String title = ul.select("a").text();
			  if(!url.startsWith("/video/data"))
			  {
				  if(url.startsWith("/"))
					 {
						 String url_base = link.substring(0,link.indexOf("/", 8));
						 url = url_base+url;
					 }
				//  Log.d("phanbom","url strong parse"+url);
				  StructurePost post = new StructurePost(null, null, title, url, cate_web);
				  posts.add(post);
			  }
		  }
		  Elements infomore = doc.select(".cnn_fabcattxt");
		  for(Element info:infomore)
		  {
			  String url = info.select("a").attr("href");
			  String title = info.select("a").text();
			  if(!url.startsWith("/video/data"))
			  {
				  if(url.startsWith("/"))
					 {
						 String url_base = link.substring(0,link.indexOf("/", 8));
						 url = url_base+url;
					 }
				//  Log.d("phanbom","url strong parse"+url);
				  StructurePost post = new StructurePost(null, null, title, url, cate_web);
				  posts.add(post);
			  }
		  }
		  
		  return posts;
	  }
	  public static ArrayList<StructurePost> parse_title_out_2(String html,int cate_web,String link)
	  {
		  ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
		  Document doc = Jsoup.parse(html);
		  Element links = doc.select("div.columnGroup").get(1);
		  Elements linkss = links.select("div.story");
		  for(Element div:linkss)
		  {
			  
			  String url = div.select("h3").select("a").attr("href");
			  String title = div.select("h3").select("a").text();
			 // Log.d("phanbom","url strong parse"+url);
			  StructurePost post = new StructurePost(null, null, title, url, cate_web);
			  posts.add(post);
		  }
		  return posts;
	  }
	  public static ArrayList<StructurePost> parse_title_out_3(String html,int cate_web,String link)
	  {
		  ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
		  Document doc = Jsoup.parse(html);
		  Elements linkss = doc.select("h3").not(".sectionHeader").not(".hover-title");
		  for(Element h3:linkss)
		  {
			  String url = h3.select("a").attr("href");
			  String title = h3.select("a").text();
			//  Log.d("phanbom","url strong parse"+url);
			  StructurePost post = new StructurePost(null, null, title, url, cate_web);
			  posts.add(post);
		  }
		  return posts;
	  }
	  public static ArrayList<StructurePost> parse_title_out_4(String html,int cate_web,String link)
	  {
		  ArrayList<StructurePost> posts = new ArrayList<StructurePost>();
		  Document doc = Jsoup.parse(html);
		  Elements group = doc.select(".content_column2_1 .boxwidgetInner").select("div.boxwidget_part>div.boxwidget_part");
		  for(Element elem:group)
		  {
			  String url = elem.select("h4").select("a").attr("href");
			  String title =elem.select("h4").select("a").text();
			  if(url.startsWith("/"))
				 {
					 String url_base = link.substring(0,link.indexOf("/", 8));
					 url = url_base+url;
				 }
			//  Log.d("phanbom","url strong parse"+url);
			  StructurePost post = new StructurePost(null, null, title, url, cate_web);
			  posts.add(post);
		  }
		  return posts;
	  }
}
