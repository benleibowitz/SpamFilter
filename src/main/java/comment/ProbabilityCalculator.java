package comment;

import lombok.NonNull;

public class ProbabilityCalculator {
    private SpamAlgorithm algorithm;

    public ProbabilityCalculator(@NonNull SpamAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isSpam(@NonNull Comment comment) {
        return algorithm.isSpam(comment);
    }
}
