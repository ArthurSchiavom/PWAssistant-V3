package com.github.ArthurSchiavom.old.questionnaire.base.question;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ReplyProcessorFunc {
	/**
	 * Processes a reply.
	 *
	 * @param event The event containing the reply.
	 */
	public void process(MessageReceivedEvent event);
}
