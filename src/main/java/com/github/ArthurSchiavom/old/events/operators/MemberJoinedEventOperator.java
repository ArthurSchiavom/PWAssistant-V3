package com.github.ArthurSchiavom.old.events.operators;

import com.github.ArthurSchiavom.old.error.Reporter;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Channels;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Guilds;
import com.github.ArthurSchiavom.old.utils.Security;
import com.github.ArthurSchiavom.old.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.time.Instant;

public class MemberJoinedEventOperator {

	public void processMemberJoinedEvent(GuildMemberJoinEvent event) {
		User user = event.getUser();
		if (user.isBot())
			return;

		long guildId = event.getGuild().getIdLong();
		if (guildId != Guilds.mainGuildId)
			return;

		String username = user.getName();
		PrivateChannel privateChannel = openPrivateChannel(user);
		if (!verifyAndProcessUser(event, privateChannel, username))
			return;

//		new PWIKingdomWelcomeQuestionnaire().startQuestionnaire(privateChannel, user.getIdLong());
		Channels.getPwiKingdomGeneralChannel()
				.sendMessage("Welcome to PWI Kingdom **" + username + "**! Make sure to read <#617691793315725317> to learn the rules and other useful com.github.ArthurSchiavom.old.information! <:ttcute:288020411126644736>")
				.queue();

		logUserJoinedEvent(event);
	}

	private void logUserJoinedEvent(GuildMemberJoinEvent event) {
		Member member = event.getMember();
		User user = member.getUser();
		String userIdentificationDisplay = String.format("%s (%s, %s)",
				member.getAsMention(),
				user.getAsTag(),
				member.getId());

		long accountAgeMillis = Instant.now().minusMillis(user.getTimeCreated().toInstant().toEpochMilli()).toEpochMilli();
		String ageDisplay = Utils.millisecondsToYearDaysHoursMinutesDisplay(accountAgeMillis);

		EmbedBuilder eb = new EmbedBuilder();
		Embeds.configDefaultEmbedColor(eb);
		eb.setTitle("New Member!")
				.setDescription(userIdentificationDisplay)
				.appendDescription(" joined the server!\n" +
						"\nCreated ")
				.appendDescription(ageDisplay)
				.appendDescription(" ago")
				.setThumbnail(user.getEffectiveAvatarUrl());

		event.getJDA().getTextChannelById(589105381784027137L).sendMessageEmbeds(eb.build()).queue();
	}

	private PrivateChannel openPrivateChannel(User user) {
		PrivateChannel privateChannel = null;
		try {
			privateChannel = user.openPrivateChannel().complete();
		} catch (Exception e) {
			Reporter.reportToPWIKingdom(user.getAsMention() + " doesn't allow DMs.");
		}
		return privateChannel;
	}

	/**
	 *
	 * @param event
	 * @param privateChannel can be null.
	 * @param username
	 * @return (1) true if the user is valid and (2) false if the user was kicked
	 */
	private boolean verifyAndProcessUser(GuildMemberJoinEvent event, PrivateChannel privateChannel, String username) {
		boolean nameHasAdvertisement = Security.stringIncludesAdvertisement(username);
		if (nameHasAdvertisement) {
			Member member = event.getMember();
			if (privateChannel != null) {
				privateChannel.sendMessage("You were kicked from PWI Kingdom because your name contains some sort of advertisement.")
						.queue(msg -> kickAndReport(username, member)
								, ex -> kickAndReport(username, member));
			}
			else {
				kickAndReport(username, member);
			}
			return false;
		}
		return true;
	}

	private void kickAndReport(String username, Member member) {
		Reporter.reportToPWIKingdom(username + " was kicked due to having advertisement in the username.");
		Guilds.getMainGuild().kick(member).reason("Username includes advertisement").queue();
	}
}
