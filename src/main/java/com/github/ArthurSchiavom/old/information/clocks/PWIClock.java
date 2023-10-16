package com.github.ArthurSchiavom.old.information.clocks;

import com.github.ArthurSchiavom.old.database.tables.PWIClockTable;
import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PWIClock extends Clock {
	private static Map<PwiServer, String> serverTimesDisplay = new HashMap<>();
	private List<PwiServer> pwiServers = new ArrayList<>();
	private final String guildId;
	private final String channelId;
	private final String msgId;

	public PWIClock(List<PwiServer> pwiServers, String guildId, String channelId, String msgId) {
		this.pwiServers.addAll(pwiServers);
		Collections.sort(this.pwiServers);
		this.guildId = guildId;
		this.channelId = channelId;
		this.msgId = msgId;
		if (serverTimesDisplay.isEmpty()) {
			serverTimesDisplay.put(PwiServer.DAWNGLORY, "not yet calculated");
			serverTimesDisplay.put(PwiServer.ETHERBLADE, "not yet calculated");
			serverTimesDisplay.put(PwiServer.TWILIGHT_TEMPLE, "not yet calculated");
			serverTimesDisplay.put(PwiServer.TIDESWELL, "not yet calculated");
		}
		updatePWITimes();
		updateClockMessage(Bot.getJdaInstance());
	}

	public PWIClock(List<PwiServer> pwiServers, long guildId, long channelId, long msgId) {
		this(pwiServers, Long.toString(guildId), Long.toString(channelId), Long.toString(msgId));
	}

	@Override
	public boolean updateClockMessage(JDA jda) {
		boolean shouldKeepClock = true;
		StringBuilder sb = new StringBuilder();
		for (PwiServer server : pwiServers) {
			sb.append("\n\n").append(serverTimesDisplay.get(server));
		}

		EmbedBuilder eb = new EmbedBuilder()
				.setTitle(":clock1: PWI Clock")
				.setDescription(sb.toString());
		Embeds.configDefaultEmbedColor(eb);

		return this.editClockMessageSync(jda, MessageEditData.fromEmbeds(eb.build()));
	}

	public synchronized static void updatePWITimes() {
		for (PwiServer server : serverTimesDisplay.keySet()) {
			serverTimesDisplay.replace(server, "**" + server.getName() + "**: "
					+ Utils.calendarToTimeDisplay(server.getCurrentTime()));
		}
	}

	public List<PwiServer> getPwiServers() {
		return new ArrayList<>(pwiServers);
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
		return PWIClockTable.getInstance().remove(msgId);
	}

	@Override
	public boolean addToDatabase() {
		return PWIClockTable.getInstance().add(this);
	}
}
