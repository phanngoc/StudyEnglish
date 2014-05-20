package structure;

public class DataCatePaper {
  private int id;
  private String regex_title_out;
  private String regex_title_in;
  private String regex_content_in;
  private String link;
  private String name;
  public DataCatePaper(int id,String name,String regex_title_out,String regex_title_in,String regex_content_in,String link)
  {
	  this.id = id; 
	  this.regex_title_out = regex_title_out;
	  this.regex_title_in = regex_title_in;
	  this.regex_content_in = regex_content_in;
	  this.link = link;
	  this.name = name;
  }
  public int getId()
  {
	  return this.id;
  }
  public String getRegexTitleOut()
  {
	  return this.regex_title_out;
  }
  public String getRegexTitleIn()
  {
	  return this.regex_title_in;
  }
  public String getLink()
  {
	  return this.link;
  }
  public String getName()
  {
	  return this.name;
  }
}
