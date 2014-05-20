package com.example.studyenglish;

public class StructureListCate {
	private int id;
	private String cate;
  public StructureListCate(int id,String cate){
	  this.id = id;
	  this.cate = cate;
  }
  public int getId(){
	  return this.id;
  }
  public String getTitle(){
	  return this.cate;
  }
  public String getImage(){
	  return null;
  }
  public void setId(int id){
	  this.id = id;
  }
  public void setTitle(String cate){
	  this.cate = cate;
  }
}
