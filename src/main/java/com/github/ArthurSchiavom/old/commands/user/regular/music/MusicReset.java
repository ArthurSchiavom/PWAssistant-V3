//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Command;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
//import com.github.ArthurSchiavom.old.commands.base.Requirement;
//import com.github.ArthurSchiavom.old.music.PlayerManager;
//import net.dv8tion.jda.api.entities.emoji.Emoji;
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//
//public class MusicReset extends CommandWithoutSubCommands {
//	public MusicReset(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Clear the com.github.ArthurSchiavom.old.music queue and stop playing."
//				, null
//				, false
//				, superCommand);
//		this.addName("Reset");
//		this.getRequirementsManager().setRequirements(Requirement.ADMIN, Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		PlayerManager.getInstance().unregisterMusicManager(event.getGuild().getIdLong());
//		event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
//	}
//}
