package com.github.ArthurSchiavom.old.commands.user.regular.utilities.serverInfo;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ServerInfo extends CommandWithoutSubCommands {
	public ServerInfo() {
		super(Category.UTILITY
				, "Information about this server"
				, null
				, true);
		this.addName("ServerInfo");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		Guild guild = event.getGuild();
		TextChannel channel = event.getGuildChannel().asTextChannel();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setThumbnail(guild.getIconUrl())
				.addField("General com.github.ArthurSchiavom.old.information", calcGeneralInfo(guild), false)
				.addField("Members", calcMembersInfo(guild), false)
				.addField("Channels", calcChannelInfo(guild), false)
				.addField("Invite", calcInviteInfo(guild, channel), false);
		Embeds.configDefaultEmbedColor(eb);
		channel.sendMessageEmbeds(eb.build()).queue();
	}

	public static String calcGeneralInfo(Guild guild) {
		OffsetDateTime creationTime = guild.getTimeCreated();
		return String.format("**%s** was created on **%d of %s %d** by **%s**." +
						"\nThere are %d custom emotes!",
				guild.getName()
				, creationTime.getDayOfMonth()
				, creationTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
				, creationTime.getYear()
				, guild.getOwner().getUser().getAsTag()
				, guild.getEmojis().size());
	}

	public static String calcMembersInfo(Guild guild) {
		Guild.MetaData metaData = guild.retrieveMetaData().complete();
		return "**Users**: " + metaData.getApproximateMembers() + " • **Online**: " + metaData.getApproximatePresences();
	}

	public static String calcChannelInfo(Guild guild) {
		int nCategories = guild.getCategories().size();
		int nTextChannels = guild.getTextChannels().size();
		int nVoiceChannels = guild.getVoiceChannels().size();
		return String.format("**Categories**: %d" +
						"\n**Channels**: %d" +
						"\n**Text Channels**: %d" +
						"\n**Voice Channels**: %d" +
						"\n**Default Channel**: %s"
				, nCategories
				, nTextChannels + nVoiceChannels
				, nTextChannels
				, nVoiceChannels
				, guild.getDefaultChannel().getAsMention());
	}

	public static String calcInviteInfo(Guild guild, TextChannel channel) {
		List<Invite> existingInvites = null;
		Invite invite = null;
		String inviteUrl;
		boolean failedRetrieveExisting = false;
		boolean failedCreateNew = false;

		try {
			existingInvites = guild.retrieveInvites().complete();

			for (Invite existingInvite : existingInvites) {
				if (!existingInvite.isTemporary() && existingInvite.getMaxAge() == 0)
					invite = existingInvite;
			}
			if (invite == null)
				failedRetrieveExisting = true;
		} catch (Exception e) {
			failedRetrieveExisting = true;
		}

		if (failedRetrieveExisting) {
			try {
				invite = channel.createInvite().setMaxAge(0).setMaxAge(0).complete();
			} catch (Exception e) {
				failedCreateNew = true;
			}
		}

        if (failedRetrieveExisting && failedCreateNew) {
        	inviteUrl = "I don't have permission to create an invite nor to see the existing ones :(";
        }
        else {
        	inviteUrl = invite.getUrl();
        }
        return inviteUrl;
	}
}
