package com.github.ArthurSchiavom.old.timer;

import com.github.ArthurSchiavom.old.timer.operation.TimerOperation;

public abstract class Timer {
	private final int SLEEP_TIME;
	private TimerOperation[] timerOperations;

	public Timer(int SLEEP_TIME, TimerOperation... timerOperations) {
		this.SLEEP_TIME = SLEEP_TIME;
		this.timerOperations = timerOperations;
	}

	public synchronized void start() {
		new Thread(() -> {
			while(true) {
				for (TimerOperation op : timerOperations) {
					try {
						op.operate();
					} catch (Exception e) {
						System.out.println("OPERATION " + op.getClass().getName() + " FAILED: \n" + e.getStackTrace());
					}
				}

				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					System.out.println("FAILED TO PUT TIMER THREAD TO SLEEP");
				}
			}
		}).start();
	}
}
