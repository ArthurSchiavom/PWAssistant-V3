package com.github.ArthurSchiavom.old.information.scheduling.messageSchedule;

import com.github.ArthurSchiavom.old.database.tables.MessagingSchedulerTable;
import com.github.ArthurSchiavom.old.information.CacheModificationSuccessState;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MessagingSchedulerRegister {
	private static List<MessagingScheduler> messagingSchedulers = Collections.synchronizedList(new ArrayList<>());

	/**
	 * Registers a messaging schedule.
	 *
	 * @param messagingScheduler The messaging schedule to register.
	 * @return (1) CacheModificationSuccessState.SUCCESS in case of success.
	 * <br>(2) CacheModificationSuccessState.FAILED_CACHE_MODIFICATION if there's already a schedule with that name in that guild.
	 * <br>(3) CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION if com.github.ArthurSchiavom.old.database modification failed.
	 */
	public static CacheModificationSuccessState register(MessagingScheduler messagingScheduler, boolean addToDatabase) {
		if (getSchedule(messagingScheduler.getGuildId(), messagingScheduler.getName()) != null) {
			return CacheModificationSuccessState.FAILED_CACHE_MODIFICATION;
		}

		if (addToDatabase && !MessagingSchedulerTable.getInstance().add(messagingScheduler)) {
			return CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION;
		}

		messagingSchedulers.add(messagingScheduler);
		return CacheModificationSuccessState.SUCCESS;
	}

	/**
	 * Unregisters a messaging schedule.
	 *
	 * @param guildId The ID of the guild where the schedule is used.
	 * @param scheduleName The name of the messaging schedule.
	 * @return (1) CacheModificationSuccessState.SUCCESS in case of success.
	 * <br>(2) CacheModificationSuccessState.FAILED_CACHE_MODIFICATION if there's no schedule with that name in that guild.
	 * <br>(3) CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION if com.github.ArthurSchiavom.old.database modification failed.
	 */
	public static CacheModificationSuccessState unregister(String guildId, String scheduleName) {
		MessagingScheduler messagingScheduler = getSchedule(guildId, scheduleName);
		return unregister(messagingScheduler);
	}

	/**
	 * Unregisters a messaging schedule.
	 *
	 * @param messagingScheduler The schedule to remove.
	 * @return (1) CacheModificationSuccessState.SUCCESS in case of success.
	 * <br>(2) CacheModificationSuccessState.FAILED_CACHE_MODIFICATION if the schedule is null (because being null can mean invalid or not found).
	 * <br>(3) CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION if com.github.ArthurSchiavom.old.database modification failed.
	 */
	public static CacheModificationSuccessState unregister(MessagingScheduler messagingScheduler) {
		if (messagingScheduler == null)
			return CacheModificationSuccessState.FAILED_CACHE_MODIFICATION;

		if (!MessagingSchedulerTable.getInstance().remove(messagingScheduler.getGuildId(), messagingScheduler.getName()))
			return CacheModificationSuccessState.FAILED_DATABASE_MODIFICATION;

		messagingSchedulers.remove(messagingScheduler);
		return CacheModificationSuccessState.SUCCESS;
	}

	/**
	 * Finds a registered schedule.
	 *
	 * @param guildId The ID of the guild.
	 * @param scheduleName The name of the schedule.
	 * @return (1) The schedule found or (2) null if no schedule was found.
	 */
	public static MessagingScheduler getSchedule(String guildId, String scheduleName) {
		synchronized (messagingSchedulers) {
			for (MessagingScheduler scheduler : messagingSchedulers) {
				if (scheduler.isThisScheduler(guildId, scheduleName))
					return scheduler;
			}
		}
		return null;
	}

	public static void checkAllSchedules() {
		Calendar timeNow = Calendar.getInstance();
		synchronized (messagingSchedulers) {
			for (MessagingScheduler messagingScheduler : messagingSchedulers) {
				messagingScheduler.checkSchedule(timeNow);
			}
		}
	}

	public static List<MessagingScheduler> getGuildSchedules(String guildId) {
		List<MessagingScheduler> guildSchedulers = new ArrayList<>();
		synchronized (messagingSchedulers) {
			for (MessagingScheduler scheduler : messagingSchedulers) {
				if (scheduler.getGuildId().equals(guildId))
					guildSchedulers.add(scheduler);
			}
		}
		return guildSchedulers;
	}
}
