package concurrent;

import concurrent.stacks.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Random;

public class ConcurrentStackThread extends Thread {

	private ConcurrentLinkedQueue<RegisterStack<Integer>> registerQueue;
	private boolean consummer;

	private CompareAndSetStack<Integer> stack;

	ConcurrentStackThread(boolean consummer, CompareAndSetStack stack) {
		this.consummer = consummer;
		this.stack = stack;

		registerQueue = new ConcurrentLinkedQueue<RegisterStack<Integer>>();
	}

	public ConcurrentLinkedQueue getRegisterQueue() {
		return registerQueue;
	}

	public void run() {
		for (int i = 0; i < 15; i++) {
			if(consummer) {
				try {
					// Creamos el registro del principio de la llamada
					RegisterStack registerBegin = new RegisterStack(this.getId(), System.currentTimeMillis(), "pop", true, null, null);
					this.registerQueue.add(registerBegin);

					// Hacemos la llamada
					Integer returnValue = stack.pop();

					// Creamos el registro del regreso de llamada
					RegisterStack registerEnd = new RegisterStack(this.getId(), System.currentTimeMillis(), "pop", false, null, returnValue);
					this.registerQueue.add(registerEnd);
				} catch (ConcurrentPopException cpe) {}

			} else {
				// Valor aleatorio
				Random random = new Random();
				int randValue = random.nextInt();

				// Creamos el registro del principio de la llamada
				RegisterStack registerBegin = new RegisterStack(this.getId(), System.currentTimeMillis(), "push", true, randValue, null);
				this.registerQueue.add(registerBegin);

				stack.push(randValue);

				// Creamos el registro del regreso de llamada
				RegisterStack registerEnd = new RegisterStack(this.getId(), System.currentTimeMillis(), "push", false, randValue, null);
				this.registerQueue.add(registerEnd);
			}
		}
	}

}