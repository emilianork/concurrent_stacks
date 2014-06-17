package concurrent.stacks;

public class BackOffStack<T> extends CompareAndSetStack<T> {
    
	protected ExponentialBackOff backoff;
	
	private static final int MIN_DELAY = 1;
	private static final int MAX_DELAY = 100;
	
	public BackOffStack() {
		super();
		backoff = new ExponentialBackOff(MIN_DELAY, MAX_DELAY);
	}

	public void push(T elem) {
		SimpleNode<T> newNode = new SimpleNode<T>(elem);
		while (true) {
			if (tryPush(newNode))
				return;
			else
				backoff.backoff();
		}
	}

	public T pop() throws ConcurrentPopException {
		while(true) {
			SimpleNode<T> node = tryPop();

			if (node != null)
				return node.getValue();
			else
				backoff.backoff();
		}
	}
}
