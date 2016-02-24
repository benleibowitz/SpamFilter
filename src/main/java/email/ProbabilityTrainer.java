package email;

public interface ProbabilityTrainer {
    // Commit / write changes in mapping
    void train(final Email email, final boolean spam);
}
