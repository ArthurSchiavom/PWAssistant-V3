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
//public class MusicVolume extends CommandWithoutSubCommands {
//	public MusicVolume(Command superCommand) {
//		super(superCommand.getCategory()
//				, "Set the volume from 1 to 100 (higher values are possible but quality is lost)."
//				, "Volume"
//				, false
//				, superCommand);
//		this.addName("Volume");
//		this.addName("V");
//		this.getRequirementsManager().setRequirements(Requirement.GUILD_HAS_MUSIC_MANAGER_ACTIVE);
//		this.buildHelpMessage();
//	}
//
//	@Override
//	protected void runCommandActions(MessageReceivedEvent event) {
//		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
//
//		PlayerManager playerManager = PlayerManager.getInstance();
//		Guild guild = event.getGuild();
//		GuildMusicManager musicManager = playerManager.getGuildMusicManager(guild.getIdLong());
//
//		try {
//			musicManager.player.setVolume(Integer.parseInt(args));
//			event.getMessage().addReaction(Emoji.fromUnicode("\uD83D\uDC4C")).queue();
//		} catch (Exception e) {
//			event.getChannel().sendMessage("That's not a valid volume level. <:ffconfused:489925231285239830>").queue();
//		}
//
//	}
//}
