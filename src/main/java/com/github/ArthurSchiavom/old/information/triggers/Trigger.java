package com.github.ArthurSchiavom.old.information.triggers;

import com.github.ArthurSchiavom.old.database.tables.TriggersTable;

public class Trigger {
	private long guildId;
	private String triggerLowerCase;
	private String reply;

	public Trigger(long guildId, String trigger, String reply) {
		this.guildId = guildId;
		this.triggerLowerCase = trigger.toLowerCase();
		this.reply = reply;
	}

	public long getGuildId() {
		return guildId;
	}

	public String getTriggerLowerCase() {
		return triggerLowerCase;
	}

	public String getReply() {
		return reply;
	}

	/**
	 * Verifies if a message contains this trigger.
	 *
	 * @param msgLowerCase The message in all-lowercase.
	 * @return If the message contains this trigger.
	 */
	public boolean containsThisTrigger(String msgLowerCase) {
		return msgLowerCase.contains(triggerLowerCase);
	}

	/**
	 * Verifies if a string represents this trigger.
	 *
	 * @param lowerCaseString The string in all-lowercase.
	 * @return If the string represents this trigger.
	 */
	public boolean isThisTrigger(String lowerCaseString) {
		return lowerCaseString.equals(triggerLowerCase);
	}

	public boolean addToDatabase() {
		return TriggersTable.getInstance().add(this);
	}

	public boolean removeFromDatabase() {
		return TriggersTable.getInstance().remove(guildId, triggerLowerCase);
	}
}
