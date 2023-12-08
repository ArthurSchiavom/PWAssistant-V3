package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question;

public class Question {
	private final String question;
	private final ReplyProcessor replyProcessor;

	public Question(String question, ReplyProcessor replyProcessor) {
		this.question = question;
		this.replyProcessor = replyProcessor;
	}

	public String getQuestionText() {
		return question;
	}

	public ReplyProcessor getReplyProcessor() {
		return replyProcessor;
	}
}
