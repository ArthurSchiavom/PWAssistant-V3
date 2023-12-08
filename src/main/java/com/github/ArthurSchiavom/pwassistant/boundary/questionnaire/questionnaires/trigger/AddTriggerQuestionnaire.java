package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.trigger;

import com.github.ArthurSchiavom.old.information.triggers.Trigger;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.Questionnaire;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhenToDeleteMessages;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhichMessagesToDelete;

public class AddTriggerQuestionnaire extends Questionnaire {
	String trigger = "ERROR";
	String reply = "ERROR";
	public AddTriggerQuestionnaire() {
		this.addQuestion("A trigger is some text that, whenever present in a message, is replied to with a given message!" +
						"\nWhat's the trigger?", event -> {
					trigger = event.getMessage().getContentRaw();
					this.nextQuestion();
				}
				, false);
		this.addQuestion("What's the reply to give when the text is detected?", event -> {
					reply = event.getMessage().getContentRaw();
					Trigger newTrigger = new Trigger(event.getGuild().getIdLong(), trigger, reply);
					TriggerRegister.getInstance().register(newTrigger, true);
					event.getChannel().sendMessage("New trigger registered!").queue();
					this.nextQuestion();
				}
				, false);
	}

	@Override
	protected void setConfiguration() {
		this.setConfigWhichMessagesToDelete(WhichMessagesToDelete.ALL);
		this.setConfigWhenToDeleteMessages(WhenToDeleteMessages.NEXT_QUESTION);
	}
}
