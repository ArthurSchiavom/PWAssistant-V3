package com.github.ArthurSchiavom.old.questionnaire.base;

import net.dv8tion.jda.api.entities.Message;

public enum WhenToDeleteMessages {
	END, NEXT_QUESTION, NEVER;

	public void addMessageToDelete(Questionnaire questionnaire, Message msg) {
		switch (this) {
			case END:
				questionnaire.queueMessageToDeleteEnd(msg);
				break;
			case NEXT_QUESTION:
				questionnaire.queueMessageToDeleteNextQuestion(msg);
		}
	}
}
