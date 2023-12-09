package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.trigger;

import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.Questionnaire;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhenToDeleteMessages;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhichMessagesToDelete;
import com.github.ArthurSchiavom.pwassistant.control.trigger.TriggerService;
import com.github.ArthurSchiavom.pwassistant.entity.Trigger;

public class AddTriggerQuestionnaire extends Questionnaire {
	private final TriggerService triggerService;
	String trigger = "ERROR";
	String reply = "ERROR";
	public AddTriggerQuestionnaire(final TriggerService triggerService) {
		this.triggerService = triggerService;
		this.addQuestion("A trigger is some text that, whenever present in a message, is replied to with a given message!" +
						"\nWhat's the trigger?", event -> {
					trigger = event.getMessage().getContentRaw();
					this.nextQuestion();
				}
				, false);
		this.addQuestion("What should I reply when the text is detected?", event -> {
					reply = event.getMessage().getContentRaw();
					final Trigger newTrigger = new Trigger(event.getGuild().getIdLong(), trigger, reply);
					triggerService.addTrigger(newTrigger);
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
