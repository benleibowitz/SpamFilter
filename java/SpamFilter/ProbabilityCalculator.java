package spam;


public class ProbabilityCalculator {
	private SpamAlgorithm algorithm;
	
	public ProbabilityCalculator(SpamAlgorithm algorithm) {
		if(algorithm == null)
			throw new IllegalArgumentException("ProbabilityCalculator arguments cannot be null");
		
		this.algorithm = algorithm;
	}
	
	public boolean isSpam(Message message) {
		if(message == null)
			throw new IllegalArgumentException("Message cannot be null");
			
		return algorithm.isSpam(message);
	}
}
