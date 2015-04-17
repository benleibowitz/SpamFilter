package email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ProbabilityCalculator {
	private SpamAlgorithm algorithm;
	
	@Autowired
	public ProbabilityCalculator(SpamAlgorithm algorithm) {
		if(algorithm == null)
			throw new IllegalArgumentException("ProbabilityCalculator arguments cannot be null");
		
		this.algorithm = algorithm;
	}
	
	public boolean isSpam(Email email) {
		if(email == null)
			throw new IllegalArgumentException("Message cannot be null");
			
		return algorithm.isSpam(email);
	}
}
