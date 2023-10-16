package com.github.ArthurSchiavom.old.error;

import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Guilds;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Users;

public class Reporter {
	public static void report(String msg) {
		Bot.getJdaInstance().getUserById(Users.ownerId).openPrivateChannel().queue(channel -> {
					channel.sendMessage(msg).queue();
				}
				, exception -> {
					System.out.println("Failed to report com.github.ArthurSchiavom.old.error: " + msg);
				});
	}

	public static void reportToPWIKingdom(String msg) {
		Guilds.getMainGuild().getTextChannelById("289853288940044290").sendMessage(msg).queue();
	}
}
