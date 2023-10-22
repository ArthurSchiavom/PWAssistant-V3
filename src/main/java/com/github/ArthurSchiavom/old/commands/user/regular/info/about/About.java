package com.github.ArthurSchiavom.old.commands.user.regular.info.about;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class About extends CommandWithoutSubCommands {
	public About() {
		super(Category.INFORMATION
				, "Information about the bot."
				, null
				, false);
		this.addName("About");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		event.getChannel().sendMessage("Hello!"
				//+ "\nMy source code is available at <https://github.com/KingOffNothing/PWAssistant>"
				+ "\n"
				+ "\nI was initially released in November 2017 with the purpose of helping administrate and serve the members of **PWIKingdom**."
				+ "\nPWI Kingdom is a Discord server for PWI players to discuss PWI stuff, just hang around, meet people, form squads, get help, stay informed, etc."
				+ "\nI also provide this server with more commands and features!"
				+ "\n"
				+ "\nWe currently have over 300 members from all servers online every day!"
				+ "\n**__If you wish to join, we'll be glad to welcome you!__**"
				+ "\n"
				+ "\n♥ \uD835\uDC08\uD835\uDC27\uD835\uDC2F\uD835\uDC22\uD835\uDC2D\uD835\uDC1E: https://discord.gg/pwi-kingdom-251460250115375114").queue();
	}
}
