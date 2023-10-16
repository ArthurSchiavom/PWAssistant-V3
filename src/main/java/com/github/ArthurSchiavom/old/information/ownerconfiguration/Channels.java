package com.github.ArthurSchiavom.old.information.ownerconfiguration;

import com.github.ArthurSchiavom.old.information.Bot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Channels {
	public static final String PWI_KINGDOM_GENERAL_CHANNEL_ID = "251460250115375114";
	public static final String BOT_LOG_CHANNEL_ID = "415250432248512522";

	public static TextChannel getPwiKingdomGeneralChannel() {
		return Bot.getJdaInstance().getTextChannelById(PWI_KINGDOM_GENERAL_CHANNEL_ID);
	}

	public static TextChannel getBotLogChannel() {
		return Bot.getJdaInstance().getTextChannelById(BOT_LOG_CHANNEL_ID);
	}
}
