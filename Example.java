import concurrent.stacks.*;
import java.util.Random;

class Example {
    
	public static void main (String[] arg) {
		System.out.println("Hello World!"); // Display the string.
        final CompareAndSetStack<Integer> stack = new CompareAndSetStack<>();
		final BackOffStack<Integer> bstack = new BackOffStack<>();
		final EliminationStack<Integer> ebstack = new EliminationStack<>(1,50);
		
        /* producer thread */
        new Thread() {
            public void run() {
                Random random = new Random();
                /* bounded loops, since the analyzer actually runs this code */
                for(int i = 0; i < 10; i++) {
                    stack.push(random.nextInt());
                }
            }
        }.start();
		
        /* consumer thread */
        new Thread() {
            public void run() {
                for(int i=0; i < 10; i++) {
					try {
						stack.pop();
					} catch (ConcurrentPopException cpe) {
					}
                }
            }
        }.start();


		 new Thread() {
            public void run() {
                Random random = new Random();
                /* bounded loops, since the analyzer actually runs this code */
                for(int i = 0; i < 10; i++) {
                    bstack.push(random.nextInt());
                }
            }
        }.start();
		
        /* consumer thread */
        new Thread() {
            public void run() {
                for(int i=0; i < 10; i++) {
					try {
						bstack.pop();
					} catch (ConcurrentPopException cpe) {
					}
                }
            }
        }.start();


		new Thread() {
            public void run() {
                Random random = new Random();
                /* bounded loops, since the analyzer actually runs this code */
                for(int i = 0; i < 10; i++) {
                    ebstack.push(random.nextInt());
                }
            }
        }.start();
		
        /* consumer thread */
        new Thread() {
            public void run() {
                for(int i=0; i < 10; i++) {
					try {
						ebstack.pop();
					} catch (ConcurrentPopException cpe) {
					}
                }
            }
        }.start();

		
		System.out.println("GoodBye World!"); // Display the string.
    }
}
