package com.github.ArthurSchiavom.old.commands.user.regular.info.invite;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Invite extends CommandWithoutSubCommands {
	public Invite() {
		super(Category.INFORMATION
				, "Invite the bot to your server!"
				, null
				, false);
		this.addName("Invite");
		this.addName("InviteBot");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		event.getChannel().sendMessage("I'm glad you like me! Use this link to invite me over: <https://discordapp.com/oauth2/authorize?client_id=377542452493680660&scope=bot&permissions=104328289>").queue();
	}
}
