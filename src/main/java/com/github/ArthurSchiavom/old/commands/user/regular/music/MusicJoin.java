//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
//import net.dv8tion.jda.api.entities.emoji.Emoji;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.api.managers.AudioManager;
//
//public class MusicJoin extends CommandWithoutSubCommands {
//	public MusicJoin(Command superCommand) {
//		super(superCommand.getCategory()
//				, "I'll join the voice channel you're in."
//				, null
//				, false
//				, superCommand);
//		this.addName("Join");
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		AudioChannel channel = event.getMember().getVoiceState().getChannel();
//		try {
//			AudioManager audioManager = event.getGuild().getAudioManager();
//			audioManager.openAudioConnection(channel);
//			event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
//		} catch (Exception e) {
//			event.getChannel().sendMessage("You aren't in any voice channel or I don't have permission to join the voice channel.").queue();
//		}
//	}
//}
