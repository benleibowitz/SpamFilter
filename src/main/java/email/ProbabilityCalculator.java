package email;

import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class ProbabilityCalculator {
    @Autowired
    private SpamAlgorithm algorithm;

    public boolean isSpam(@NonNull Email email) {
        return algorithm.isSpam(email);
    }
}
