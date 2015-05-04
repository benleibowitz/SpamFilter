package data;

public class GenericWord extends Word {
    @Override
    public int getSpamCount() {
      throw new UnsupportedOperationException("Cannot get word count on generic word. Generic word has no word count");
    }
    
    @Override
    public void setSpamCount(int spamCount) {
      throw new UnsupportedOperationException("Cannot set word count on generic word. Generic word has no word count");
    }
    
    @Override
    public int getRealCount() {
      throw new UnsupportedOperationException("Cannot get word count on generic word. Generic word has no word count")
    }
    
    @Override
    public void setRealCount(int realCount) {
      throw new UnsupportedOperationException("Cannot set word count on generic word. Generic word has no word count")
    }
}
