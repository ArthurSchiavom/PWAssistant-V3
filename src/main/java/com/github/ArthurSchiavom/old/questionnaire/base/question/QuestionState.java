package com.github.ArthurSchiavom.old.questionnaire.base.question;

import java.util.Calendar;

/**
 * Object that represents a question's state. One object per question, it is updated.
 */
public class QuestionState {
	private boolean answered;
	private final Calendar expirationTime;

	public QuestionState(boolean answered, int expirationTimeSeconds) {
		this.answered = answered;
		expirationTime = Calendar.getInstance();
		expirationTime.add(Calendar.SECOND, expirationTimeSeconds);
	}

	public boolean wasAnswered() {
		return answered;
	}

	public void setAnsweredState(boolean answered) {
		this.answered = answered;
	}

	/**
	 * Checks if the question expired.
	 *
	 * @param currentTime Calendar with the current time.
	 * @return If the question expired.
	 */
	public boolean expired(Calendar currentTime) {
		return currentTime.after(expirationTime);
	}
}
