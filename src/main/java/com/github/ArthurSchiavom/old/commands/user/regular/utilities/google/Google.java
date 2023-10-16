package com.github.ArthurSchiavom.old.commands.user.regular.utilities.google;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Google extends CommandWithoutSubCommands {
	public Google() {
		super(Category.UTILITY
				, "Search something on google."
				, "What to search"
				, false);
		this.addName("Google");
		this.addExample("aurora borealis", "Search com.github.ArthurSchiavom.old.information about aurora borealis on google.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		if (args == null) {
			event.getChannel().sendMessage("You must tell me what to search for.").queue();
			return;
		}

		args = args.replace(' ', '+');
		event.getChannel().sendMessage("<https://www.google.com/search?q=" + args + ">").queue();
	}
}
