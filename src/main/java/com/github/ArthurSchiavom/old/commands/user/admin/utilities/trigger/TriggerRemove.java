package com.github.ArthurSchiavom.old.commands.user.admin.utilities.trigger;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.CacheModificationSuccessState;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TriggerRemove extends CommandWithoutSubCommands {
	public TriggerRemove(Command superCommand) {
		super(superCommand.getCategory()
				, "Remove a trigger."
				, "TriggerName"
				, true
				, superCommand);
		this.addName("Remove");
		this.addName("R");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null) {
			event.getChannel().sendMessage("You need to tell me the name of the trigger to remove!").queue();
			return;
		}

		CacheModificationSuccessState successState = TriggerRegister.getInstance().unregister(event.getGuild().getIdLong(), args);
		String reply;
		switch (successState) {
			case SUCCESS:
				reply = "Trigger removed successfully!";
				break;
			case FAILED_CACHE_MODIFICATION:
				reply = "No trigger named **" + args + "** was found.";
				break;
			case FAILED_DATABASE_MODIFICATION:
				reply = "Oh no! I failed to access my com.github.ArthurSchiavom.old.database :worried:\nIf this com.github.ArthurSchiavom.old.error persists, please report it in the support server.";
				break;
			default:
				reply = "An unknown com.github.ArthurSchiavom.old.error occurred.";
		}
		event.getChannel().sendMessage(reply).queue();
	}
}
