package com.github.ArthurSchiavom.old.commands.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Callback for an operation that verifies if a given event meets the command running requirements.
 */
public interface RequirementVerification {
	/**
	 * Verifies if an event meets this requirement.
	 *
	 * @param event The event to verify.
	 * @return If the event meets the requirement.
	 */
	public boolean meetsRequirement(MessageReceivedEvent event);
}
