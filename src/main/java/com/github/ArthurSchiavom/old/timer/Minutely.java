package com.github.ArthurSchiavom.old.timer;

import com.github.ArthurSchiavom.old.timer.operation.updateClocks.ClockUpdater;
import com.github.ArthurSchiavom.old.timer.operation.updateMessagingSchedulers.MessagingSchedulerUpdater;

/**
 * Timer that runs every minute. <b>Register the operations at start().</b>
 */
public class Minutely extends Timer {
	private static final int SLEEP_TIME = 60000;

	public Minutely() {
		super(SLEEP_TIME, new ClockUpdater(), new MessagingSchedulerUpdater());
	}
}
