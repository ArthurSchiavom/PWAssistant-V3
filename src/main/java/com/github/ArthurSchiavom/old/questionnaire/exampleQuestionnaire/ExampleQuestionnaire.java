package com.github.ArthurSchiavom.old.questionnaire.exampleQuestionnaire;

import com.github.ArthurSchiavom.old.questionnaire.base.Questionnaire;
import com.github.ArthurSchiavom.old.questionnaire.base.WhenToDeleteMessages;
import com.github.ArthurSchiavom.old.questionnaire.base.WhichMessagesToDelete;

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
