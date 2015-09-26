package email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import data.Word;
import data.WordDAO;

public class BayesEmailProbabilityTrainer implements ProbabilityTrainer {
    private WordDAO wordDAO;

    @Autowired
    public BayesEmailProbabilityTrainer(WordDAO wordDAO) {
        if (wordDAO == null)
            throw new IllegalArgumentException("Scoring system cannot be null");

        this.wordDAO = wordDAO;
    }

    @Override
    public void train(Email email, boolean spam) {
        if (email == null)
            throw new IllegalArgumentException("Message cannot be null");

        train(email.getBody(), spam, Email.Source.BODY);
        train(email.getSender(), spam, Email.Source.SENDER);
        train(email.getSubject(), spam, Email.Source.SUBJECT);
    }

    private void train(String text, boolean spam, Email.Source source) {
        List<Word> genericWords = wordDAO.getGenericWords();

        String[] wordsInText = text.split(" ");
        for (int i = 0; i < wordsInText.length; i++) {

            // Setup String[] with current word, and combo of (previous word +
            // current word)
            String[] wordOrPhrase;

            if (i == 0)
                wordOrPhrase = new String[] { wordsInText[i] };
            else
                wordOrPhrase = new String[] { wordsInText[i],
                        wordsInText[i - 1] + " " + wordsInText[i] };

            
            for (String wordString : wordOrPhrase) {
                Word word = new Word(wordString);
                
                if(!genericWords.contains(word)) {
                    boolean shouldInsertNewWord = false;
                    
                    word = wordDAO.getWord(wordString, source);
                    if(word == null) {
                        word = new Word(wordString);
                        shouldInsertNewWord = true;
                    }
                    
                    if(spam)
                        word.setSpamCount(word.getSpamCount() + 1);
                    else
                        word.setRealCount(word.getRealCount() + 1);
                    
                    if(shouldInsertNewWord)
                        wordDAO.insert(word, source);
                    else
                        wordDAO.update(word, source);
                }
                
            }
        }
    }
}
