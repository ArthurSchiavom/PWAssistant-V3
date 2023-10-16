//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.commands.base.Requirement;
//import com.github.ArthurSchiavom.old.music.GuildMusicManager;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import net.dv8tion.jda.api.entities.Guild;
//import net.dv8tion.jda.api.entities.emoji.Emoji;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//
//public class MusicResume extends CommandWithoutSubCommands {
//	public MusicResume(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Pause the com.github.ArthurSchiavom.old.music."
//				, null
//				, false
//				, superCommand);
//		this.addName("Resume");
//		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		Guild guild = event.getGuild();
//		GuildMusicManager musicManager = PlayerManager.getInstance().getInstance()
//				.getGuildMusicManager(guild.getIdLong());
//
//		musicManager.player.setPaused(false);
//		event.getMessage().addReaction(Emoji.fromUnicode("▶")).queue();
//	}
//}
