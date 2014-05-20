package structure;

public class Newspaper {
  private int id;
  private String name;
  private String logo;
  public Newspaper(String logo,int id,String name)
  {
	  this.id = id;
	  this.name = name; 
	  this.logo = logo;
  }
  public int getId()
  {
	  return this.id;
  }
  public String getString()
  {
	  return this.name;
  }
  public String getLogo()
  {
	 return this.logo; 
  }
}
