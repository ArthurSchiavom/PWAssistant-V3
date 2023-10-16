package com.github.ArthurSchiavom.old.questionnaire.base;

import com.github.ArthurSchiavom.old.questionnaire.base.question.MessageType;

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
