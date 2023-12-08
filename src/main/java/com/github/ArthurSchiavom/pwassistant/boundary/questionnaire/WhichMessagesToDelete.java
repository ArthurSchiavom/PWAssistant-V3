package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire;

import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question.MessageType;

import java.util.Arrays;
import java.util.List;

public enum WhichMessagesToDelete {
	ALL(MessageType.values()), QUESTIONS(MessageType.QUESTION), ANSWERS(MessageType.ANSWER), NONE;

	private List<MessageType> appliableTypes;
	WhichMessagesToDelete(MessageType... msgTypes) {
		if (msgTypes == null)
			msgTypes = new MessageType[0];
		appliableTypes = Arrays.asList(msgTypes);
	}

	public boolean appliesTo(MessageType msgType) {
		return appliableTypes.contains(msgType);
	}
}
