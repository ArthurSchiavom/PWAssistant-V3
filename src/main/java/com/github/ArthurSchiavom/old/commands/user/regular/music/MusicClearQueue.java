//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.commands.base.Requirement;
//import com.github.ArthurSchiavom.old.music.GuildMusicManager;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import net.dv8tion.jda.api.entities.emoji.Emoji;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//
//public class MusicClearQueue extends CommandWithoutSubCommands {
//	public MusicClearQueue(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Clear the com.github.ArthurSchiavom.old.music queue but keep playing the current song."
//				, null
//				, false
//				, superCommand);
//		this.addName("ClearQueue");
//		this.getRequirementsManager().setRequirements(Requirement.ADMIN, Requirement.SAME_VOICE_CHANNEL);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild().getIdLong());
//
//		if (musicManager == null) {
//			event.getChannel().sendMessage("**There's nothing playing.**").queue();
//			return;
//		}
//
//		musicManager.scheduler.clearQueue();
//		event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
//	}
//}
