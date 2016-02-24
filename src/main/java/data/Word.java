package data;

public class Word {
    private String word;
    private int spamCount;
    private int realCount;
    
    public Word() {
        
    }
    
    public Word(String word) {
        this.word = word;
    }
    
    public int getSpamCount() {
        return spamCount;
    }
    public void setSpamCount(int spamCount) {
        this.spamCount = spamCount;
    }
    public int getRealCount() {
        return realCount;
    }
    public int incrementRealCount() {
        return ++realCount;
    }
    public int incrementSpamCount() {
        return ++spamCount;
    }
    public void setRealCount(int realCount) {
        this.realCount = realCount;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((word == null) ? 0 : word.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        Word other = (Word) obj;
        if (word == null) {
            if (other.word != null)
                return false;
        } else if (!word.equals(other.word))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return word;
    }
    
}
