package com.github.ArthurSchiavom.old.questionnaire.base.question;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.old.questionnaire.base.Questionnaire;
import com.github.ArthurSchiavom.old.timer.operation.checkExpiredQuestions.ExpirablesChecker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuestionManager {
	private int expirationTimeSeconds = 30;

	private List<Question> questions = new ArrayList<>();
	private int nQuestion = -1;
	private final Questionnaire questionnaire;
	private QuestionState currentQuestionState;

	public QuestionManager(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public void setExpirationTimeSeconds(int expirationTimeSeconds) {
		this.expirationTimeSeconds = expirationTimeSeconds;
	}

	/**
	 * Adds a question to this com.github.ArthurSchiavom.old.questionnaire.
	 *
	 * @param question The question to ask.
	 * @param questionReplyProcessorFunc The reply processor.
	 */
	public void addQuestion(String question, ReplyProcessorFunc questionReplyProcessorFunc, boolean runOnNewThread) {
		String questionText = question;
		ReplyProcessor replyProcessor = new ReplyProcessor(questionReplyProcessorFunc, runOnNewThread);
		questions.add(new Question(questionText, replyProcessor));
	}


	/**
	 * Move to the updateNextExecutionTime question.
	 *
	 * @return (1) The updateNextExecutionTime question text or (2) null if no questions are left.
	 */
	public String goToNextQuestion() {
		setCurrentQuestionAnsweredState(true);
		nQuestion++;
		return getCurrentQuestionText(true);
	}

	/**
	 * Move to the previous question.
	 *
	 * @return (1) The previous question text or (2) null if no questions are left.
	 */
	public String goToPreviousQuestion() {
		setCurrentQuestionAnsweredState(true);
		nQuestion--;
		return getCurrentQuestionText(true);
	}

	/**
	 * Move back N questions. <b>Counting starts on 0.</b>
	 *
	 * @return The question text.
	 * @throws IndexOutOfBoundsException If there is no question with such number.
	 */
	public String goBackNQuestions(int n) {
		return goToQuestion(nQuestion - n);
	}

	/**
	 * Move forward N questions. <b>Counting starts on 0.</b>
	 *
	 * @return The question text.
	 * @throws IndexOutOfBoundsException If there is no question with such number.
	 */
	public String goForwardNQuestions(int n) {
		return goToQuestion(nQuestion + n);
	}

	/**
	 * Move to a given question. <b>Counting starts on 0.</b>
	 *
	 * @return The question text.
	 * @throws IndexOutOfBoundsException If there is no question with such number
	 */
	public String goToQuestion(int questionNumber) {
		setCurrentQuestionAnsweredState(true);
		int previousNumber = nQuestion;
		nQuestion = questionNumber;
		String questionText = getCurrentQuestionText(true);
		if (questionText == null) {
			nQuestion = previousNumber;
			throw new IndexOutOfBoundsException("There is no qustion with such number");
		}
		else
			return questionText;
	}

	/**
	 * Safely changes the answered state of the current question (checking if there's a current question).
	 *
	 * @param state The new state.
	 */
	public void setCurrentQuestionAnsweredState(boolean state) {
		if (currentQuestionState != null) {
			currentQuestionState.setAnsweredState(state);
		}
	}

	/**
	 * Get the current question.
	 *
	 * @return (1) The updateNextExecutionTime question or (2) null if no questions are left.
	 */
	public String getCurrentQuestionText(boolean initializeTimer) {
		try {
			if (initializeTimer) {
				initializeAnswerExpirationTimer(currentQuestionState);
			}
			return questions.get(nQuestion).getQuestionText();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void processReply(MessageReceivedEvent event) {
		questions.get(nQuestion).getReplyProcessor().process(event);
	}

	private void initializeAnswerExpirationTimer(QuestionState state) {
		currentQuestionState = new QuestionState(false, expirationTimeSeconds);
		ExpirablesChecker.addExpirable(new QuestionExpirable(currentQuestionState, this));
	}

	/**
	 * Checks if the current question expired.
	 *
	 * @param currentTime Calendar representing the current time.
	 * @return If the question expired and no longer needs to be checked.
	 */
	public boolean checkExpiration(QuestionState questionState, Calendar currentTime) {
		boolean expired = currentQuestionState.expired(currentTime);
		if (expired && !questionState.wasAnswered()) {
			questionnaire.questionnaireTimedOut();
		}
		return expired;
	}

	/**
	 * Signals the end of the necessity for this question manager.
	 */
	public void end() {
		setCurrentQuestionAnsweredState(true);
	}


}
