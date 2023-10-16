package com.github.ArthurSchiavom.old.information.clocks;

import com.github.ArthurSchiavom.old.information.Bot;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.JDA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClockRegister {
	private static ClockRegister instance;
	private ClockRegister() {}
	public static void initialize() {
		if (instance == null)
			instance = new ClockRegister();
	}
	public static ClockRegister getInstance() {
		return instance;
	}

	public static final int MAX_CLOCKS_PER_GUILD = 10;
	private final List<Clock> clocksRegistered = Collections.synchronizedList(new ArrayList<>());

	/**
	 * Registers a clock.
	 *
	 * @param clock The clock to register.
	 * @param addToDatabase If the clock should be added to the com.github.ArthurSchiavom.old.database.
	 * @return If the register was successful. It is unsuccessful if:
	 * <br>1. The guild has more than MAX_CLOCKS_PER_GUILD clocks active.
	 * <br>2. Failed to access the com.github.ArthurSchiavom.old.database.
	 */
	public boolean register(Clock clock, boolean addToDatabase) {
		boolean success = true;
		if (canClockBeRegistered(clock)) {
			clocksRegistered.add(clock);
			if (addToDatabase) {
				clock.addToDatabase();
			}
		}
		else
			success = false;
		return success;
	}

	private boolean canClockBeRegistered(Clock clock) {
		String guildId = clock.getGuildId();
		return getNClocksOnGuild(guildId) < MAX_CLOCKS_PER_GUILD;
	}

	private int getNClocksOnGuild(String guildId) {
		int count = 0;
		synchronized (clocksRegistered) {
			for (Clock clock : clocksRegistered) {
				if (clock.getGuildId().equals(guildId))
					count++;
			}
		}
		return count;
	}

	public void updateAllClocks() {
		JDA jdaInstance = Bot.getJdaInstance();
		synchronized (clocksRegistered) {
			PWIClock.updatePWITimes();
			CountdownClock.updateCurrentTime();
			List<Clock> clocksToRemove = new ArrayList<>();
			Iterator<Clock> it = clocksRegistered.iterator(); // Must be in synchronized block
			while (it.hasNext()) {
				Clock clock = it.next();
				if (!clock.updateClockMessage(jdaInstance))
					clocksToRemove.add(clock);
			}

			for (Clock clockToRemove : clocksToRemove) {
				unregister(clockToRemove);
			}
		}
	}

	private void unregister(Clock clock) {
		clocksRegistered.remove(clock);
		clock.removeFromDatabase();
	}
}
