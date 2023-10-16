//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//
//public class MusicPlayNext extends CommandWithoutSubCommands {
//	public MusicPlayNext(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Have the bot play some com.github.ArthurSchiavom.old.music. The bot must be in a voice channel already!"
//				, "Youtube com.github.ArthurSchiavom.old.music link"
//				, false
//				, superCommand);
//		this.addName("PlayNext");
//		this.buildHelpMessage();
//	}
//
//	@SuppressWarnings("Duplicates")
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
//		MusicPlay.play(args, event, true);
//	}
//}
