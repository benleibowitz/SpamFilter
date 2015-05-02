package email;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
    public void commit() {
    }

    @Override
    public void train(Email email, boolean spam) {
        if (email == null)
            throw new IllegalArgumentException("Message cannot be null");

        train(email.getBody(), spam, wordDAO.getBodyCountMap());
        train(email.getSender(), spam, wordDAO.getSenderCountMap());
        train(email.getSubject(), spam, wordDAO.getSubjectCountMap());
    }

    private void train(String text, boolean spam,
            Map<String, int[]> wordCountMap) {
        List<String> genericWords = wordDAO.getGenericWords();

        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {

            // Setup String[] with current word, and combo of (previous word +
            // current word)
            String[] wordOrPhrase;

            if (i == 0)
                wordOrPhrase = new String[] { words[i] };
            else
                wordOrPhrase = new String[] { words[i],
                        words[i - 1] + " " + words[i] };

            for (String word : wordOrPhrase) {
                int[] probs;

                if (!(genericWords.contains(word))) {

                    if (wordCountMap.containsKey(word))
                        // If word found in map, increment
                        probs = wordCountMap.get(word);
                    else
                        // Word is not in map. Add it
                        probs = new int[] { 0, 0 };

                    if (spam)
                        probs[0] += 1;
                    else
                        probs[1] += 1;

                    wordCountMap.put(word, probs);
                }
            }
        }
    }
}
