package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Class that processes com.github.ArthurSchiavom.pwassistant.boundary.questionnaire replies.
 */
public class ReplyProcessor {
	private final boolean runOnNewThread;
	private final ReplyProcessorFunc replyProcessorFunc;

	public ReplyProcessor(ReplyProcessorFunc replyProcessorFunc, boolean runOnNewThread) {
		this.runOnNewThread = runOnNewThread;
		this.replyProcessorFunc = replyProcessorFunc;
	}

	/**
	 * Processes a reply. Controls how the processing is made based on the settings, such as running on a new thread or not.
	 *
	 * @param event The event containing the reply.
	 */
	public void process(MessageReceivedEvent event) {
		if (runOnNewThread) {
			new Thread(() -> replyProcessorFunc.process(event)).start();
		}
		else {
			replyProcessorFunc.process(event);
		}
	}
}
