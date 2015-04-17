package email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class TestAlgo implements SpamAlgorithm {
    @Autowired
    public TestAlgo(BayesEmailScoringSystem scoringSystem) {
        
    }
    
    public TestAlgo() {
        
    }
    
	@Override
	public boolean isSpam(Email email) {
		
		//I call this one, AssumptionAlgorithm :)
		System.out.println("Running test algorithm on email from sender: " + email.getSender());
		return true;
	}
}
