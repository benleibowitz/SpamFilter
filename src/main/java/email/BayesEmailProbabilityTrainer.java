package email;

import data.GenericWordRepository;
import data.WordEntity;
import data.WordRepository;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

@Setter
public class BayesEmailProbabilityTrainer implements ProbabilityTrainer {
    @Autowired
    private GenericWordRepository genericWordRepository;

    @Autowired
    private WordRepository wordRepository;

    @Override
    public void train(@NonNull Email email, boolean spam) {
        train(email.getBody(), spam, Email.Source.BODY);
        train(email.getSender(), spam, Email.Source.SENDER);
        train(email.getSubject(), spam, Email.Source.SUBJECT);
    }

    private void train(String text, boolean spam, Email.Source source) {
        String[] wordsInText = text.split(" ");

        for (int i = 0; i < wordsInText.length; i++) {

            // Setup String[] with current word, and combo of (previous word +
            // current word)
            String[] wordOrPhrase;

            if (i == 0) {
                wordOrPhrase = new String[]{wordsInText[i]};
            } else {
                wordOrPhrase = new String[]{wordsInText[i], wordsInText[i - 1] + " " + wordsInText[i]};
            }

            Arrays.stream(wordOrPhrase)
                    .forEach(word -> {
                        if (!genericWordRepository.existsById(word)) {
                            WordEntity wordEntity = wordRepository.findById(new WordEntity.WordEntityPrimaryKey(word, source))
                                    .orElse(WordEntity.builder()
                                            .word(word)
                                            .source(source)
                                            .build());
                            if (spam) {
                                wordEntity.incrementSpamCount();
                            } else {
                                wordEntity.incrementRealCount();
                            }
                            wordRepository.saveAndFlush(wordEntity);
                        }
                    });
        }
    }
}
