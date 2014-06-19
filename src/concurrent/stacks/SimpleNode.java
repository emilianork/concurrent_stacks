/**
 * @author Emiliano Cabrera (jemiliano.cabrera@gmail.com)
 */
package concurrent.stacks;

/**
 * A simple implementation of a node.
 */
public class SimpleNode<T> {
	private T value;
	private SimpleNode<T> next;
	
	public SimpleNode() {
		this.value = null;
		this.next = null;
	}
	
	public SimpleNode(T value) {
		this.value = value;
		this.next = null;
	}

	public SimpleNode<T> getNext() {
		return this.next;
	}
	
	public void setNext(SimpleNode<T> next) {
		this.next = next;
	}
	
	public T getValue() {
		return this.value;
	}
	
}
