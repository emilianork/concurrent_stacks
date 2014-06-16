import concurrent.stacks.*;
import java.util.Random;

class Example {
    
	public static void main (String[] arg) {
		System.out.println("Hello World!"); // Display the string.
        final CompareAndSetStack<Integer> stack = new CompareAndSetStack<>();
		
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
    }
}
