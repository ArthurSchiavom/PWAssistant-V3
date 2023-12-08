package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.exampleQuestionnaire;

import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.Questionnaire;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhenToDeleteMessages;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhichMessagesToDelete;

/**
 * Questionnaire to announce a message.
 */
public class ExampleQuestionnaire extends Questionnaire {

	private String title;
	public ExampleQuestionnaire() {
		this.addQuestion("What's the announcement's title?"
				, event -> {
					this.queueMessageToDeleteEnd(event.getMessage());
					title = event.getMessage().getContentRaw();
					this.nextQuestion();
					this.queueMessageToDeleteEnd(event.getMessage());
					this.queueMessageToDeleteNextQuestion(event.getMessage());
				}
				, false);
	}

	@Override
	protected void setConfiguration() {
		this.setConfigWhichMessagesToDelete(WhichMessagesToDelete.ALL);
		this.setConfigWhenToDeleteMessages(WhenToDeleteMessages.NEXT_QUESTION);
	}
}
