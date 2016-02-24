package email;

import data.WordRepository;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class BayesEmailProbabilityTrainer implements ProbabilityTrainer {
    @Autowired
    private WordRepository wordRepository;

    @Override
    public void train(@NonNull Email email, boolean spam) {
        train(email.getBody(), spam, Email.Source.BODY);
        train(email.getSender(), spam, Email.Source.SENDER);
        train(email.getSubject(), spam, Email.Source.SUBJECT);
    }

    private void train(String text, boolean spam, Email.Source source) {
//        List<SenderWordEntity> genericWords = wordRepository.getGenericWords();
//
//        String[] wordsInText = text.split(" ");
//        for (int i = 0; i < wordsInText.length; i++) {
//
//            // Setup String[] with current word, and combo of (previous word +
//            // current word)
//            String[] wordOrPhrase;
//
//            if (i == 0)
//                wordOrPhrase = new String[] { wordsInText[i] };
//            else
//                wordOrPhrase = new String[] { wordsInText[i],
//                        wordsInText[i - 1] + " " + wordsInText[i] };
//
//
//            for (String wordString : wordOrPhrase) {
//                SenderWordEntity word = new SenderWordEntity(wordString);
//
//                if(!genericWords.contains(word)) {
//                    boolean shouldInsertNewWord = false;
//
//                    word = wordRepository.getWord(wordString, source);
//                    if(word == null) {
//                        word = new SenderWordEntity(wordString);
//                        shouldInsertNewWord = true;
//                    }
//
//                    if(spam) {
//                        word.incrementSpamCount();
//                    } else {
//                        word.incrementRealCount();
//                    }
//
//                    if(shouldInsertNewWord) {
//                        wordRepository.insert(word, source);
//                    } else {
//                        wordRepository.update(word, source);
//                    }
//                }
//
//            }
//        }
    }
}
