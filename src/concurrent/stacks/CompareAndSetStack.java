
/**
 * @author Emiliano Cabrera (jemiliano.cabrera@gmail.com)
 */
package concurrent.stacks;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple implementation of a free lock stack with AtomicReferences.
 * The CompareAndSetStack Class implement push() and pop() only with 
 * the compareAndSet atomic operation.
 **/
public class CompareAndSetStack <T> {

	/**
	 * The AtomicReference that represents the top of the Stack.
	 */
	protected AtomicReference<SimpleNode<T>> top;

	/**
	 * A new CompareAndSetStack instance.
	 */
	public CompareAndSetStack() {
		this.top = new AtomicReference<SimpleNode<T>>();
	}

	/**
	 * Auxiliary method of {@link push}
	 * @param node The new node we want on the top
	 * @return True if {@link node} is the new top, false otherwise.
	 */
	private boolean tryPush(SimpleNode<T> node) {
		SimpleNode<T> oldTop = top.get();
		node.setNext(oldTop);
		
		return top.compareAndSet(oldTop, node);
	}

	/**
	 * This method tries to insert the new element using compareAndSet operation.
	 * @param elem The new element we want on the top.
	 */
    public void push(T elem) {
        SimpleNode<T> newNode = new SimpleNode<T>(elem);
		while (true) {
			if (tryPush(newNode))
				return;
		}
	}
	
	/**
	 * Auxiliary method of {@link pop}
	 * @return top The top of the Stack.
	 */
	private SimpleNode<T> tryPop() throws ConcurrentPopException {
		SimpleNode<T> oldTop = top.get();
		if (oldTop == null) 
			throw new ConcurrentPopException();

		SimpleNode next = oldTop.getNext();
		if (top.compareAndSet(oldTop, next)) 
			return oldTop; 
		else 
			return null;
	}

	/**
	 * This method tries to pop the top of the stack.
	 * @return The top of the stack.
	 */
    public T pop() throws ConcurrentPopException {
		while(true) {
			SimpleNode<T> node = tryPop();

			if (node != null)
				return node.getValue();
		}
    }
}
