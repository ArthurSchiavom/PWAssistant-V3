package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire;

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
