package data;

public class GenericWord extends Word {
    public int getSpamCount() {
      throw new UnsupportedOperationException("Cannot get word count on generic word. Generic word has no word count");
    }
    public void setSpamCount(int spamCount) {
      throw new UnsupportedOperationException("Cannot set word count on generic word. Generic word has no word count");
    }
    public int getRealCount() {
      throw new UnsupportedOperationException("Cannot get word count on generic word. Generic word has no word count")
    }
    public void setRealCount(int realCount) {
      throw new UnsupportedOperationException("Cannot set word count on generic word. Generic word has no word count")
    }
}
