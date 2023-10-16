package com.github.ArthurSchiavom.old.information.clocks;

import com.github.ArthurSchiavom.old.database.tables.CountdownClockTable;
import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

public class CountdownClock extends Clock {
	private static Instant currentTime;
	private final Instant endTime;
	private final String guildId;
	private final String channelId;
	private final String msgId;

	public CountdownClock(Instant endTime, String guildId, String channelId, String msgId) {
		this.endTime = endTime;
		this.guildId = guildId;
		this.channelId = channelId;
		this.msgId = msgId;
		updateCurrentTime();
		updateClockMessage(Bot.getJdaInstance());
	}

	public CountdownClock(Instant endTime, long guildId, long channelId, long msgId) {
		this(endTime, Long.toString(guildId), Long.toString(channelId), Long.toString(msgId));
	}

	@Override
	public boolean updateClockMessage(JDA jda) {
		boolean shouldKeepClock = true;

		Duration timeLeft = Duration.between(currentTime, endTime);
		boolean isTimeOver = timeLeft.isNegative();
		String clockDurationDisplay;
		if (isTimeOver){
			clockDurationDisplay = "COUNTDOWN OVER!";
		}
		else {
			clockDurationDisplay = Utils.calcDurationDisplayEmoji(timeLeft);
		}

		EmbedBuilder eb = new EmbedBuilder()
				.setTitle(":clock1: \uD835\uDC02\uD835\uDC28\uD835\uDC2E\uD835\uDC27\uD835\uDC2D\uD835\uDC1D\uD835\uDC28\uD835\uDC30\uD835\uDC27 :clock330:")
				.setDescription(clockDurationDisplay);

		Embeds.configDefaultEmbedColor(eb);
		shouldKeepClock = this.editClockMessageSync(jda, MessageEditData.fromEmbeds(eb.build()));

		if (isTimeOver)
			shouldKeepClock = false;

		return shouldKeepClock;
	}

	public synchronized static void updateCurrentTime() {
		currentTime = Calendar.getInstance().toInstant();
	}

	@Override
	public String getGuildId() {
		return guildId;
	}

	@Override
	public String getChannelId() {
		return channelId;
	}

	@Override
	public String getMessageId() {
		return msgId;
	}

	@Override
	public boolean removeFromDatabase() {
		return CountdownClockTable.getInstance().remove(msgId);
	}

	@Override
	public boolean addToDatabase() {
		return CountdownClockTable.getInstance().add(this);
	}

	public Instant getEndTime() {
		return endTime;
	}
}
