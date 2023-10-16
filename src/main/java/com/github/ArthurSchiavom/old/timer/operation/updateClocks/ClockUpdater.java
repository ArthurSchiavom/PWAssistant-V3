package com.github.ArthurSchiavom.old.timer.operation.updateClocks;

import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.clocks.ClockRegister;
import com.github.ArthurSchiavom.old.timer.operation.TimerOperation;

public class ClockUpdater implements TimerOperation {
	@Override
	public void operate() {
		if (!Bot.isTestAccount())
			ClockRegister.getInstance().updateAllClocks();
	}
}
