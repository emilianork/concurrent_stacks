/**
 * @author Emiliano Cabrera (jemiliano.cabrera@gmail.com)
 */

package concurrent.stacks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.Random;

public class EliminationArray<T> {
	private int duration;
	private int capacity;

	LockFreeExchanger<T>[] exchanger;

	Random random;

	public EliminationArray(int capacity, int duration) {
		exchanger = (LockFreeExchanger<T>[]) new LockFreeExchanger[capacity];

		for (int i = 0; i < capacity; i++)
			exchanger[i] = new LockFreeExchanger<T>();
		
		random = new Random();
		
		this.duration = duration;
		this.capacity = capacity;
	}

	public T visit(T value) throws TimeoutException {
		int slot = random.nextInt(capacity);

		return (exchanger[slot].exchange(value, this.duration,
										 TimeUnit.MILLISECONDS));
	}
}
