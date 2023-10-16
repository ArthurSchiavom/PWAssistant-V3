//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.commands.base.Requirement;
//import com.github.ArthurSchiavom.old.music.GuildMusicManager;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import com.github.ArthurSchiavom.old.music.TrackScheduler;
//import net.dv8tion.jda.api.entities.emoji.Emoji;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//
//public class MusicSkip extends CommandWithoutSubCommands {
//	public MusicSkip(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Ask the bot to skip the current song."
//				, null
//				, false
//				, superCommand);
//		this.addName("Skip");
//		this.addName("Next");
//		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		PlayerManager playerManager = PlayerManager.getInstance();
//		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild().getIdLong());
//
//		TrackScheduler scheduler = musicManager.scheduler;
//		AudioPlayer player = musicManager.player;
//
//		if (player.getPlayingTrack() == null) {
//			event.getChannel().sendMessage("Not playing anything!").queue();
//			return;
//		}
//		scheduler.nextTrack(true);
//
//		event.getMessage().addReaction(Emoji.fromUnicode("⏩")).queue();
//	}
//}
