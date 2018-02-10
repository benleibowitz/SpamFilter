package comment;

import lombok.NonNull;

import java.util.Map;

public class BayesCommentProbabilityTrainer implements ProbabilityTrainer {
    private BayesCommentScoringSystem scoringSystem;

    public BayesCommentProbabilityTrainer(@NonNull BayesCommentScoringSystem scoringSystem) {
        this.scoringSystem = scoringSystem;
    }

    @Override
    public void commit() {
        scoringSystem.write();
    }

    @Override
    public void train(@NonNull Comment comment, boolean spam) {
        train(comment.getBody(), spam, scoringSystem.getTrainingCountMap());
    }

    private void train(String text, boolean spam,
            Map<String, double[]> probabilityMap) {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {

            // Setup String[] with current word, and combo of (current word +
            // previous word)
            String[] wordOrPhrase;

            if (i == 0) {
                wordOrPhrase = new String[]{words[i]};
            } else {
                wordOrPhrase = new String[]{words[i],
                        words[i - 1] + " " + words[i]};
            }

            for (String word : wordOrPhrase) {
                double[] probs;

                if (probabilityMap.containsKey(word)) {
                    // If word found in map, increment existing array
                    probs = probabilityMap.get(word);
                } else {
                    // SenderWordEntity is not in map. Add it
                    probs = new double[] { 0, 0 };
                }

                if (spam) {
                    probs[0] += 1;
                } else {
                    probs[1] += 1;
                }

                probabilityMap.put(word, probs);
            }
        }
    }
}
