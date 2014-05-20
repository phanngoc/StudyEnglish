package com.example.bom;

public class StructureSentences {
	private int id;
	private String content;
	private String meaning;
	private String example;
	private String type;
  public StructureSentences(int id,String content,String meaning,String example,String type){
	  this.id = id;
	  this.content = content;
	  this.meaning = meaning;
	  this.example = example;
	  this.type = type;
  }
  public StructureSentences(){
	  
  }
  public void setId(int id){
	  this.id = id; 
  }
  public void setContent(String content){
	  this.content = content;
  }
  public void setMeaning(String meaning){
	  this.meaning = meaning;
  }
  public void setExample(String example){
	  this.example = example;
  }
  public void setType(String type){
	  this.type = type;
  }
  
  public int getId(){
	  return this.id; 
  }
  public String getContent(){
	  return this.content;
  }
  public String getMeaning(){
	  return this.meaning;
  }
  public String getExample(){
	  return this.example ;
  }
  public String getType(){
	  return this.type;
  }
}
