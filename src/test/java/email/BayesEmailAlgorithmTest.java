package email;

import org.junit.Test;

public class BayesEmailAlgorithmTest {

    @Test
    public void testIsSpam() {
        BayesEmailScoringSystem scoringSystem = new BayesEmailScoringSystem();
        SpamAlgorithm bayes = new BayesEmailAlgorithm(scoringSystem);
        
        Email email = new Email("test sender", "test subject", "test body");
        bayes.isSpam(email);
    }

}
