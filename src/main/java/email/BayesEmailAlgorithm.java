/*		
 * The calculation for this Bayes algorithm is:		
 * for each word, W, in an email message		
 * the probability P(S|W) that given W, the message is spam		
 * is:		
 * 	P(S|W) = P(W|S) * P(S) / ( P(W|S) * P(S) + P(W|R) * P(R) )		
 *		 WHERE:		
 *		 P(W|S) -> probability that word W appears in spam messages		
 *		 P(S)   -> probability that any message is spam		
 *		 P(W|R) -> probability that word W appears in real messages		
 *		 P(R)   -> probability that any message is real (1 - P(S))		
 *		
 * The combined probability P that an entire message is spam is calculated as:		
 * 	P = 1 / (1 + e^n)		
 * 		WHERE:		
 *		n	-> SUM(1->N){ ln(1 - P(S|Wi)) - ln( P(S|Wi) ) }		
 * 		P(S|Wi)	-> P(S|W) for each word Wi
 *		
 */
package email;

import data.GenericWordRepository;
import data.WordEntity;
import data.WordRepository;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Setter
@Slf4j
public class BayesEmailAlgorithm implements SpamAlgorithm {
    private static final double BODY_WEIGHT = 0.35;
    private static final double SENDER_WEIGHT = 0.2;
    private static final double SUBJECT_WEIGHT = 0.45;
    private static final double SMOOTHING = 1;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private GenericWordRepository genericWordRepository;

    @Override
    public boolean isSpam(@NonNull final Email email) {
        double weightedProbability = BODY_WEIGHT * processWord(email.getBody(), Email.Source.BODY)
                + SENDER_WEIGHT * processWord(email.getSender(), Email.Source.SENDER)
                + SUBJECT_WEIGHT * processWord(email.getSubject(), Email.Source.SUBJECT);

        log.debug("Weighted spam probability: " + weightedProbability);
        return (weightedProbability > 0.5);
    }

    private double processWord(@NonNull final String text, final Email.Source source) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("Processing text [%s] from source [%s]", text, source.name()));
        }
        double sumLogsSpam = 0;

        String[] bodyWords = text.split(" ");

        /*
         * For each word in message body, check the both the current word and
         * combo of (previous word + current word) for matches in the mapping.
         * EXAMPLE: if word is "here" and previous word is "click", get
         * P(S|"here") and P(S|"click here")
         */
        for (int i = 0; i < bodyWords.length; i++) {
            String[] wordCombos = new String[]{bodyWords[i]};

            if (i > 0) {
                String adjacentWords = bodyWords[i - 1] + " " + bodyWords[i];
                wordCombos = new String[]{bodyWords[i], adjacentWords};
            }

            double sumLogs = Arrays.stream(wordCombos)
                    .filter(w -> !genericWordRepository.existsById(w))
                    .map(wordOrPhrase -> wordRepository.findById(new WordEntity.WordEntityPrimaryKey(wordOrPhrase, source))
                            .map(this::calculateLog)
                            .orElse(0.0))
                    .mapToDouble(Double::valueOf)
                    .sum();
            sumLogsSpam += sumLogs;
        }

        double probSpam = 1 / (1 + Math.pow(Math.E, sumLogsSpam));
        if (log.isDebugEnabled()) {
            log.debug(String.format("Probability spam [%.5f] from source [%s]", probSpam, source.name()));
        }
        return probSpam;
    }

    private double calculateLog(WordEntity wordEntity) {
        double smoothedRealCount = wordEntity.getRealCount() + SMOOTHING;
        double smoothedSpamCount = wordEntity.getSpamCount() + SMOOTHING;
        double totalWords = smoothedRealCount + smoothedSpamCount;
        double probSpamWord = smoothedSpamCount / totalWords;
        double probRealWord = smoothedRealCount / totalWords;

        // Check threshold and add to total probability
        if (Math.abs(0.5 - probSpamWord) > LEGITIMATE_WORD_THRESHOLD) {

            double pSpamNumerator = probSpamWord * PROBABILITY_SPAM_MESSAGE;
            double pDenom = (probSpamWord * PROBABILITY_SPAM_MESSAGE)
                    + (probRealWord * (1 - PROBABILITY_SPAM_MESSAGE));

            return (Math.log(1 - pSpamNumerator / pDenom) - Math.log(pSpamNumerator / pDenom));
        }
        return 0;
    }
}
