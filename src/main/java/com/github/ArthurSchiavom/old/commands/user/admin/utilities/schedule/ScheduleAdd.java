package com.github.ArthurSchiavom.old.commands.user.admin.utilities.schedule;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ScheduleAdd extends CommandWithoutSubCommands {
	public ScheduleAdd(Command superCommand) {
		super(superCommand.getCategory()
				, "Add a new messaging schedule!"
				, null
				, false
				, superCommand);
		this.addName("Add");
		this.addName("A");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
//		new AddMessagingScheduleQuestionnaire().startQuestionnaire(event.getChannel(), event.getAuthor().getIdLong());
	}
}
