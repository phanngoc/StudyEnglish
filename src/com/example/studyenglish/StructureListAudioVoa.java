package com.example.studyenglish;

import android.os.Parcel;
import android.os.Parcelable;

public class StructureListAudioVoa implements Parcelable {
  private int id;
  private String title;
  private String content;
  private String link;
  public static final Parcelable.Creator<StructureListAudioVoa> CREATOR = new Parcelable.Creator<StructureListAudioVoa>() {

	  public StructureListAudioVoa createFromParcel(Parcel pc) {

	  return new StructureListAudioVoa(pc);

	  }

	  public StructureListAudioVoa[] newArray(int size) {

	  return new StructureListAudioVoa[size];

	  }

	  };
			public StructureListAudioVoa(int id,String title,String content,String link){
				this.id=id;
				this.title = title;
				this.content = content;
				this.link = link;
			}
   public StructureListAudioVoa(Parcel pc) {
		// TODO Auto-generated constructor stub
	   id = pc.readInt();
	   title = pc.readString();
	   content = pc.readString();
	   link = pc.readString();
	}
//   get	
   public int getId(){
	   return this.id;
   }
   public String getTitle(){
	   return this.title;
   }
   public String getContent(){
	   return this.content;
   }
   public String getLink()
   {
	   return this.link;
   }
   public String getImage()
   {
	   return null;
   }
   
   //  set
   public void setId(int id){
	   this.id = id;
   }
   public void setTitle(String title){
	   this.title = title;
   }
   public void setContent(String content){
	   this.content = content;
   }
   public void setLink(String link){
	   this.link = link;
   }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeInt(this.id);
		arg0.writeString(this.title);
		arg0.writeString(this.content);
		arg0.writeString(this.link);
	}
   
}
