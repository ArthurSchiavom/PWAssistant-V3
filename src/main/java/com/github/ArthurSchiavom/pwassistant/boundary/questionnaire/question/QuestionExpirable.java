package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question;

import com.github.ArthurSchiavom.old.timer.operation.checkExpiredQuestions.Expirable;

import java.util.Calendar;

public class QuestionExpirable implements Expirable {
	private final QuestionState questionState;
	private final QuestionManager questionManager;

	public QuestionExpirable(QuestionState questionState, QuestionManager questionManager) {
		this.questionState = questionState;
		this.questionManager = questionManager;
	}

	/**
	 * Checks if the current question expired.
	 *
	 * @param currentTime Calendar representing the current time.
	 * @return If the question expired and no longer needs to be checked.
	 */
	@Override
	public boolean check(Calendar currentTime) {
		return questionManager.checkExpiration(questionState, currentTime);
	}
}
