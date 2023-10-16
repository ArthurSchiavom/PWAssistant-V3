package com.github.ArthurSchiavom.old.commands.base;

import com.github.ArthurSchiavom.old.information.admins.AdminsManager;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Guilds;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Users;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * A command requirement (to be used).
 */
public enum Requirement {
	ADMIN(event -> {
		Member member = event.getMember();
		if (member == null) {
			event.getChannel().sendMessage("This command can only be used in servers.").queue();
			return false;
		}
		else {
			boolean isAdmin = AdminsManager.getInstance().isAdmin(member);
			if (!isAdmin) {
				event.getChannel().sendMessage("This command can only be used by admins.").queue();
			}
			return isAdmin;
		}
	})

	, MAIN_GUILD(event -> {
		if (event.getGuild().getIdLong() == Guilds.mainGuildId)
			return true;
		else {
			event.getChannel().sendMessage("This command can only be used in the main guild (PWI Kingdom).").queue();
			return false;
		}
	})

	, OWNER(event -> {
		if (event.getAuthor().getIdLong() == Users.ownerId)
			return true;
		else {
			event.getChannel().sendMessage("This command can only be used by the bot owner.").queue();
		}
		return event.getAuthor().getIdLong() == Users.ownerId;
	})
	, SAME_VOICE_CHANNEL (event -> {
		MessageChannel channel = event.getChannel();

		AudioManager audioManager = event.getGuild().getAudioManager();

		if (!audioManager.isConnected()) {
			channel.sendMessage("I'm not connected to a voice channel!").queue();
			return false;
		}

		AudioChannel voiceChannel = audioManager.getConnectedChannel();
		if (!voiceChannel.getMembers().contains(event.getMember())) {
			channel.sendMessage("You have to be in the same voice channel as me to use this command!").queue();
			return false;
		}

		return true;
	})
	, BOT_CONNECTED_TO_VOICE_CHANNEL(event -> {
		MessageChannel channel = event.getChannel();

		AudioManager audioManager = event.getGuild().getAudioManager();
		if (!audioManager.isConnected()) {
			channel.sendMessage("I'm not connected to a voice channel!").queue();
			return false;
		}

		return true;
	})
//	, GUILD_HAS_MUSIC_MANAGER_ACTIVE(event -> {
//		GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild().getIdLong());
//
//		if (musicManager == null) {
//			event.getChannel().sendMessage("**There's nothing playing.**").queue();
//			return false;
//		}
//
//		return true;
//	})
	;

	private RequirementVerification requirementVerification;

	Requirement(RequirementVerification requirementVerification) {
		this.requirementVerification = requirementVerification;
	}

	/**
	 * Verifies if this requirement is met by an event and informs the user if it is not met.
	 *
	 * @param event The even to analyze.
	 * @return If the event meets this requirement.
	 */
	public boolean meetsRequirements(MessageReceivedEvent event) {
		return requirementVerification.meetsRequirement(event);
	}
}
