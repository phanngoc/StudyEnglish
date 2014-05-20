package structure;

public class WordSave {
  private String word;
  private String meaning;
  private int post_id;
  public WordSave(String word,String meaning,int post_id)
  {
	  this.word = word;
	  this.meaning = meaning;
	  this.post_id = post_id;
  }
  public String getWord()
  {
	  return word;
  }
  public String getMeaning()
  {
	  return meaning;
  }
  public int getPostId()
  {
	  return this.post_id;
  }
}
