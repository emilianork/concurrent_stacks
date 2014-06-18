package concurrent;

import concurrent.stacks.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import processing.core.*;
import java.util.concurrent.ConcurrentLinkedQueue;

class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame(); // Frame
		PApplet sketch = new ConcurrentStacksSketch();

		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setSize(400, 400);
		frame.getContentPane().add(sketch, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		sketch.init();
	}

	public static class ConcurrentStacksSketch extends PApplet {

		// Stacks
		final BackOffStack<Integer> stack = new BackOffStack<>();

		public int nConsummers = 1000;
		public int nProducers = 100;

		ConcurrentStackThread[] threads;
		int[] colors;

		public void setup() {
			// Tomamos un timeStamp del inicio
			long timeStampInit = System.currentTimeMillis();

			threads = new ConcurrentStackThread[nProducers + nConsummers];
			colors = new int[nProducers + nConsummers];

			colorMode(HSB, 360, 100, 100);

			// Creamos los threads
			int i = 0;
			for (; i < nProducers; i++) {
				threads[i] = new ConcurrentStackThread(false, stack);
			}

			for (; i < (nProducers + nConsummers); i++) {
				threads[i] = new ConcurrentStackThread(true, stack);
			}

			// Ponemos a los threads a correr
			for (i = 0; i < threads.length; i++) {
				colors[i] = color(map((float) i, 0.0f, (float) (threads.length), 0.0f, 360.0f), 100.0f, 100.0f);
				threads[i].start();
			}

			noLoop();

			// Esperamos a que todos los threads hayan terminado
			while(!allThreadsFinished()) {}

			long duration = System.currentTimeMillis() - timeStampInit;
			System.out.println(duration);

			size((int) duration * 2, threads.length * 25);
			drawExecution();
		}

		public void draw() {}

		private boolean allThreadsFinished() {
			boolean allFinished = true;

			for (int i = 0; i < threads.length; i++) {
				allFinished = allFinished && (threads[i].getState() == Thread.State.TERMINATED);
			}

			return allFinished;
		}

		private void drawExecution() {
			for (int i = 0; i < threads.length; i++) {
				System.out.println("\nThread " + threads[i].getId());

				ConcurrentLinkedQueue<RegisterStack<Integer>> registers = threads[i].getRegisterQueue();
				while(!registers.isEmpty()) {

					RegisterStack<Integer> registerBegin = registers.poll();
					RegisterStack<Integer> registerEnd = registers.poll();

					System.out.println(registerEnd.timeStamp - registerBegin.timeStamp);
				}

			}
		}

	}

}
