//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import net.dv8tion.jda.api.entities.AudioChannel;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.api.managers.AudioManager;
//
//public class MusicPlay extends CommandWithoutSubCommands {
//	public MusicPlay(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Have the bot play some com.github.ArthurSchiavom.old.music. The bot must be in a voice channel already!"
//				, "Youtube com.github.ArthurSchiavom.old.music link"
//				, false
//				, superCommand);
//		this.addName("Play");
//		this.addName("P");
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
//		MusicPlay.play(args, event, false);
//	}
//
//	public static void play(String args, MessageReceivedEvent event, boolean queueOnFront) {
//		if (args == null) {
//			event.getChannel().sendMessage("You need to specify a com.github.ArthurSchiavom.old.music link!").queue();
//			return;
//		}
//
//		AudioManager audioManager = event.getGuild().getAudioManager();
//		if (!audioManager.isConnected()) {
//			AudioChannel vc = event.getMember().getVoiceState().getChannel();
//			audioManager.openAudioConnection(vc);
//		}
//
//		try {
//			PlayerManager manager = PlayerManager.getInstance();
//			manager.loadAndPlay(event.getGuildChannel().asTextChannel(), args, queueOnFront);
//			manager.getGuildMusicManager(event.getGuild().getIdLong()).player.setPaused(false);
//		} catch (Exception e) {
//			event.getChannel().sendMessage("Either the link is invalid or I have no permission to play in the channel.").queue();
//		}
//	}
//}
