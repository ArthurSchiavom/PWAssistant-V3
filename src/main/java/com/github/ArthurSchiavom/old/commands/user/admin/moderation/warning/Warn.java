package com.github.ArthurSchiavom.old.commands.user.admin.moderation.warning;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Warn extends CommandWithoutSubCommands {
	public Warn(Command superCommand) {
		super(Category.MODERATION
				, "Warn an user."
				, "@CacheUser Reason"
				, true
				, superCommand);
		this.addName("Warn");
		this.addExample("@King Spamming", "Warns the user King for spamming.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {

	}
}
