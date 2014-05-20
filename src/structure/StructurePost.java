package structure;

import android.os.Parcel;
import android.os.Parcelable;

public class StructurePost implements Parcelable{
	private String title;
	private String url;
	private int cate_web;
	private String image;
	private String content;
    private boolean check;
    private int id;
  public  StructurePost(String image,String content,String title,String url,int cate_web){
	  this.title = title;
	  this.url = url;
	  this.cate_web = cate_web;
	  this.image = image;
	  this.content = content;
  }
  public StructurePost(Parcel source){
	  this.title = source.readString();
	  this.url = source.readString();
	  this.cate_web = source.readInt();
	  this.image = source.readString();
	  this.content = source.readString();
	  this.id = source.readInt();
  }
  
  public static final Parcelable.Creator<StructurePost> CREATOR = new Parcelable.Creator<StructurePost>() {
      public StructurePost createFromParcel(Parcel source) {
          return new StructurePost(source);
      }

      public StructurePost[] newArray(int size) {
          return new StructurePost[size];
      }
  };

  /**
   * describe the kind of special object
   */
  @Override
  public int describeContents() {
      // hashCode() of this class
      return hashCode();
  }

  /**
   * write this object in to a Parcel
   */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(title);
      dest.writeString(url);
      dest.writeInt(cate_web);
      dest.writeString(image);
      dest.writeString(content);
      dest.writeInt(id);
  }
  /*Getter*/

  public String getTitle()
  {
	  return this.title;
  }
  public String getUrl(){
	  return this.url;
  }
  public int getCateWeb(){
	  return this.cate_web;
  }
  public String getImage(){
	  return this.image;
  }
  public String getContent(){
	  return this.content;
  }
  public boolean getCheck()
  {
	  return this.check;
  }
  public int getId()
  {
	  return this.id;
  }
  /*Setter*/

  public void setTitle(String title)
  {
	  this.title = title;
  }
  public void setUrl(String url){
	  this.url = url;
  }
  public void setCateWeb(int cate_web){
	  this.cate_web = cate_web;
  }
  public void setImage(String image)
  {
	  this.image = image;
  }
  public void setContent(String content)
  {
	  this.content = content;
  }
  public void setCheck(boolean check)
  {
	  this.check = check;
  }
  public void setId(int id)
  {
	  this.id = id;
  }
}
