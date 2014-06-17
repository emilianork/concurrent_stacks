package concurrent.stacks;

import java.util.concurrent.atomic.AtomicReference;

public class CompareAndSetStack <T> {
    
	protected AtomicReference<SimpleNode<T>> top;
	
	public CompareAndSetStack() {
		this.top = new AtomicReference<SimpleNode<T>>();
	}
	
	protected boolean tryPush(SimpleNode<T> node) {
		SimpleNode<T> oldTop = top.get();
		node.setNext(oldTop);
		
		return top.compareAndSet(oldTop, node);
	}
	
    public void push(T elem) {
        SimpleNode<T> newNode = new SimpleNode<T>(elem);
		while (true) {
			if (tryPush(newNode))
				return;
		}
	}

	protected SimpleNode<T> tryPop() throws ConcurrentPopException {
		SimpleNode<T> oldTop = top.get();
		if (oldTop == null) 
			throw new ConcurrentPopException();

		SimpleNode next = oldTop.getNext();
		if (top.compareAndSet(oldTop, next)) 
			return oldTop; 
		else 
			return null;
	}
	
    public T pop() throws ConcurrentPopException {
		while(true) {
			SimpleNode<T> node = tryPop();

			if (node != null)
				return node.getValue();
		}
    }
}
