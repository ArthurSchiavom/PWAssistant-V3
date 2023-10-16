package com.github.ArthurSchiavom.old.information.triggers;

import com.github.ArthurSchiavom.old.database.tables.TriggersTable;
import com.github.ArthurSchiavom.old.information.CacheModificationSuccessState;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriggerRegister {
	private static TriggerRegister instance;
	private TriggerRegister() {}
	public static void initialize() {
		if (instance == null)
			instance = new TriggerRegister();
	}
	public static TriggerRegister getInstance() {
		return instance;
	}

	private Map<Long, List<Trigger>> guildsTriggers = Collections.synchronizedMap(new HashMap<>());

	public boolean register(Trigger trigger, boolean addToDatabase) {
		boolean success = true;

		long guildId = trigger.getGuildId();
		List<Trigger> guildTriggers = guildsTriggers.get(guildId);

		if (guildTriggers == null) {
			List<Trigger> newTriggerList = Collections.synchronizedList(new ArrayList<>());
			guildsTriggers.put(guildId, newTriggerList);
			guildTriggers = guildsTriggers.get(guildId);
		}

		guildTriggers.add(trigger);
		if (addToDatabase)
			success = trigger.addToDatabase();

		return success;
	}

	/**
	 * Processes an event. If any trigger is detected, proper actions are taken.
	 *
	 * @param event The event to analyse.
	 * @return If the event contained any triggers.
	 */
	public boolean processEvent(MessageReceivedEvent event) {
		List<Trigger> triggersPresent = findTriggersInMessage(event.getGuild().getIdLong(), event.getMessage().getContentRaw());
		if (triggersPresent.isEmpty())
			return false;

		MessageChannel channel = event.getChannel();
		for (Trigger trigger : triggersPresent) {
			channel.sendMessage(trigger.getReply()).queue();
		}
		return true;
	}

	/**
	 * Retrieves the triggers contained a message.
	 *
	 * @param guildId The ID of the guild.
	 * @param message The message to analyze.
	 * @return The triggers contained in the message.
	 */
	private List<Trigger> findTriggersInMessage(Long guildId, String message) {
		List<Trigger> triggersContained = new ArrayList<>();
		List<Trigger> guildTriggers = guildsTriggers.get(guildId);
		if (guildTriggers == null)
			return triggersContained;

		message = message.toLowerCase();
		synchronized (guildTriggers) {
			for (Trigger trigger : guildTriggers) {
				if (trigger.containsThisTrigger(message))
					triggersContained.add(trigger);
			}
		}
		return triggersContained;
	}

	private List<Trigger> retrieveTriggers(Long guildId, String triggerName) {
		List<Trigger> triggersMatching = new ArrayList<>();
		List<Trigger> guildTriggers = guildsTriggers.get(guildId);
		if (guildTriggers == null)
			return triggersMatching;

		triggerName = triggerName.toLowerCase();
		synchronized (guildTriggers) {
			for (Trigger trigger : guildTriggers) {
				if (trigger.isThisTrigger(triggerName))
					triggersMatching.add(trigger);
			}
		}
		return triggersMatching;
	}

	/**
	 * Unregisters a trigger. (From memory and com.github.ArthurSchiavom.old.database)
	 *
	 * @param guildId The ID of the guild that has the trigger.
	 * @param triggerName The trigger message.
	 * @return SUCCESS in case of success
	 * <br>FAILED_CACHE_MODIFICATION if the trigger wasn't found
	 * <br>FAILED_DATABASE_MODIFICATION if failed because the com.github.ArthurSchiavom.old.database was not reachable. (cache was left unchanged as well)
	 */
	public CacheModificationSuccessState unregister(long guildId, String triggerName) {
		List<Trigger> guildTriggers = guildsTriggers.get(guildId);
		if (guildTriggers == null)
			return CacheModificationSuccessState.FAILED_CACHE_MODIFICATION;

		triggerName = triggerName.toLowerCase();
		List<Trigger> triggersToRemove = retrieveTriggers(guildId, triggerName);

		CacheModificationSuccessState successState;
		if (triggersToRemove.isEmpty()) {
			return CacheModificationSuccessState.FAILED_CACHE_MODIFICATION;
		}
		else {
			for (Trigger triggerToRemove : triggersToRemove) {
				boolean databaseModificationSuccess = TriggersTable.getInstance().remove(triggerToRemove.getGuildId(), triggerToRemove.getTriggerLowerCase());
				if (databaseModificationSuccess)
					guildTriggers.remove(triggerToRemove);
				else
					return CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION;
			}
		}
		return CacheModificationSuccessState.SUCCESS;
	}

	public List<Trigger> getGuildTriggers(long guildId) {
		return guildsTriggers.get(guildId);
	}
}
