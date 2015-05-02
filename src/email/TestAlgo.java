package email;

import org.springframework.beans.factory.annotation.Autowired;

public class TestAlgo implements SpamAlgorithm {
    @Autowired
    public TestAlgo(BayesEmailScoringSystem scoringSystem) {

    }

    public TestAlgo() {

    }

    @Override
    public boolean isSpam(Email email) {

        System.out.println("Running test algorithm on email.");
        System.out.println("Sender: " + email.getSender());
        System.out.println("Subject: " + email.getSubject());
        System.out.println("Body: " + email.getBody());

        // I call this AssumptionAlgorithm :)
        return true;
    }
}
