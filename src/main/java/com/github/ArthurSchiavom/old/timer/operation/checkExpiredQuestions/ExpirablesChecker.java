package com.github.ArthurSchiavom.old.timer.operation.checkExpiredQuestions;

import com.github.ArthurSchiavom.old.timer.operation.TimerOperation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ExpirablesChecker implements TimerOperation {
	private static List<Expirable> expirables = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void operate() {
		List<Expirable> expirablesToUnregister = new ArrayList<>();
		Calendar currentTime = Calendar.getInstance();
		synchronized (expirables) {
			for (Expirable expirable : expirables) {
				if (expirable.check(currentTime))
					expirablesToUnregister.add(expirable);
			}
		}

		for (Expirable expirable : expirablesToUnregister)
			expirables.remove(expirable);
	}

	/**
	 * Registers a new question to be checked.
	 *
	 * @param expirable The expirable to register.
	 */
	public static void addExpirable(Expirable expirable) {
		expirables.add(expirable);
	}
}
