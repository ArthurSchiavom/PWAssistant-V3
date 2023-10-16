package com.github.ArthurSchiavom.old.events.operators;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.old.questionnaire.base.QuestionnaireRegister;

public class PrivateMessageEventOperator {
	public void processMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		QuestionnaireRegister.getInstance().processPossibleReply(event);
	}
}
