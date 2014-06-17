package concurrent.stacks;

import java.util.Random;

public class ExponentialBackOff {
	
	private final int minDelay, maxDelay;
	private int limit;
	private final Random random;
	
	public ExponentialBackOff(int minDelay, int maxDelay) {
		this.minDelay = minDelay;
		this.maxDelay = maxDelay;
		limit = minDelay; 
		random = new Random();
	}
	
	public void backoff() {
		int delay = random.nextInt(limit);
		limit = Math.min(maxDelay, 2 * limit);
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException ie) {
		}
	} 
}
