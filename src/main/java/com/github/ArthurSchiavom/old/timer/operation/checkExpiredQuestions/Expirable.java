package com.github.ArthurSchiavom.old.timer.operation.checkExpiredQuestions;

import java.util.Calendar;

/**
 * Can expire.
 */
public interface Expirable {

	/**
	 * Checks if this expirable expired.
	 *
	 * @param currentTime Calendar representing the current time.
	 * @return If the expirable expired and no longer should to be checked.
	 */
	boolean check(Calendar currentTime);
}
