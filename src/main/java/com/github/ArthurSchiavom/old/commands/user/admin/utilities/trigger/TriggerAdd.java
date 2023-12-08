package com.github.ArthurSchiavom.old.commands.user.admin.utilities.trigger;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.trigger.AddTriggerQuestionnaire;

public class TriggerAdd extends CommandWithoutSubCommands {
	public TriggerAdd(Command superCommand) {
		super(superCommand.getCategory()
				, "Add a new trigger."
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
		new AddTriggerQuestionnaire().startQuestionnaire(event.getChannel(), event.getAuthor().getIdLong());
	}
}
