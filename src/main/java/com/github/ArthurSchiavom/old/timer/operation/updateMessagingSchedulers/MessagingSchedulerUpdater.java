package com.github.ArthurSchiavom.old.timer.operation.updateMessagingSchedulers;

import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingSchedulerRegister;
import com.github.ArthurSchiavom.old.timer.operation.TimerOperation;

public class MessagingSchedulerUpdater implements TimerOperation {
	@Override
	public void operate() {
		if (!Bot.isTestAccount())
			MessagingSchedulerRegister.checkAllSchedules();
	}
}
