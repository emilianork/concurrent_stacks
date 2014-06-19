package concurrent.stacks;

import java.util.concurrent.TimeoutException;

public class EliminationStack<T> extends BackOffStack<T> {
    
	protected EliminationArray<T> eArray;

	public EliminationStack(int capacity, int duration) {
		super();

		eArray = new EliminationArray<T>(capacity, duration);
	}

	public void push(T elem) {
		SimpleNode<T> newNode = new SimpleNode<T>(elem);
		while (true) {
			if (tryPush(newNode)) {
				return;
			} else {
				try {
					T otherValue = eArray.visit(elem);
					
					if (otherValue == null)
						return; //Exchanged with pop;
				} catch (TimeoutException toe) {
					backoff.backoff();
				}
			}
		}
	}

	public T pop() throws ConcurrentPopException {
		while(true) {
			SimpleNode<T> node = tryPop();

			if (node != null) {
				return node.getValue();
			} else {
				try {
					T otherValue = eArray.visit(null);

					if (otherValue != null)
						return otherValue;
					
				} catch (TimeoutException toe) {
					backoff.backoff();
				}
			}
		}
	}
}
