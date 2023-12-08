package com.github.ArthurSchiavom.old.events.operators;

import com.github.ArthurSchiavom.old.commands.CommandExecutor;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.QuestionnaireRegister;

/**
 * Guild message com.github.ArthurSchiavom.old.events processor
 */
public class GuildMessageEventOperator {

	/**
	 * Processes a GuildMessageReceivedEvent event.
	 *
	 * @param event The event to process.
	 */
	public void processMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		GuildMessageDeleteEventOperator.storeMessage(event.getMessage());

		if (!QuestionnaireRegister.getInstance().processPossibleReply(event)) {
			CommandExecutor.getInstance().executePossibleCommandRequest(event);
		}
		TriggerRegister.getInstance().processEvent(event);
	}
}
