package structure;

public class Temp {
  private int id;
  private String key;
  private String value;
  public Temp(String key,String value)
  {
	  this.key = key;
	  this.value = value;
  }
  public void setId(int id)
  {
	  this.id = id;
  }
  public String getKey()
  {
	  return this.key;
  }
  public String getValue()
  {
	  return this.value;
  }
  
}
