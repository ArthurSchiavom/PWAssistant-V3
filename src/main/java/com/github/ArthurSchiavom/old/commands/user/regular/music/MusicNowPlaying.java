//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.commands.base.Requirement;
//import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
//import com.github.ArthurSchiavom.old.music.GuildMusicManager;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.entities.Guild;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import com.github.ArthurSchiavom.old.utils.Utils;
//
//public class MusicNowPlaying extends CommandWithoutSubCommands {
//	public MusicNowPlaying(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Stop playing com.github.ArthurSchiavom.old.music and leave the voice channel."
//				, null
//				, false
//				, superCommand);
//		this.addName("NowPlaying");
//		this.addName("NP");
//		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		PlayerManager playerManager = PlayerManager.getInstance();
//		Guild guild = event.getGuild();
//		GuildMusicManager musicManager = playerManager.getGuildMusicManager(guild.getIdLong());
//
//		AudioPlayer player = musicManager.player;
//		AudioTrack track = player.getPlayingTrack();
//		if (track == null) {
//			event.getChannel().sendMessage("**There's nothing playing.**").queue();
//			return;
//		}
//		AudioTrackInfo trackInfo = musicManager.player.getPlayingTrack().getInfo();
//		EmbedBuilder eb = new EmbedBuilder()
//				.setTitle("NOW PLAYING")
//				.setDescription("[" + trackInfo.title + "](" + trackInfo.uri + ") (" + Utils.millisecondsToMinuteSecondDisplay(track.getPosition()) + "/" + Utils.millisecondsToMinuteSecondDisplay(track.getDuration()) + ")");
//		Embeds.configDefaultEmbedColor(eb);
//		eb.setFooter("\uD83C\uDFB6");
//		event.getChannel().sendMessageEmbeds(eb.build()).queue();
//	}
//}
