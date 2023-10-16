package com.github.ArthurSchiavom.old.timer;

import com.github.ArthurSchiavom.old.timer.operation.checkExpiredQuestions.ExpirablesChecker;

public class HalfMinutely extends Timer {
	private static final int SLEEP_TIME = 30000;

	public HalfMinutely() {
		super(SLEEP_TIME, new ExpirablesChecker());
	}
}
