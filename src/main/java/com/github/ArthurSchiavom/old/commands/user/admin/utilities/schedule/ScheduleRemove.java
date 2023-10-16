package com.github.ArthurSchiavom.old.commands.user.admin.utilities.schedule;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.CacheModificationSuccessState;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingScheduler;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingSchedulerRegister;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ScheduleRemove extends CommandWithoutSubCommands {
	public ScheduleRemove(Command superCommand) {
		super(superCommand.getCategory()
				, "Remove a schedule."
				, "ScheduleName"
				, true
				, superCommand);
		this.addName("Remove");
		this.addName("R");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.addExample("EventReminder", "Removes the schedule called `EventReminder`.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null) {
			channel.sendMessage("You must tell me the name of the schedule to remove ;)").queue();
			return;
		}

		String guildId = event.getGuild().getId();
		MessagingScheduler messagingScheduler = MessagingSchedulerRegister.getSchedule(guildId, args);
		if (messagingScheduler == null) {
			channel.sendMessage("No schedule was found by that name.").queue();
			return;
		}

		CacheModificationSuccessState successState = MessagingSchedulerRegister.unregister(messagingScheduler);
		String reply;
		switch (successState) {
			case FAILED_DATABASE_MODIFICATION:
				reply = "Failed to access the com.github.ArthurSchiavom.old.database <:png230:489925231444754462>";
				break;
			case SUCCESS:
				reply = "Schedule removed!";
				break;
			default:
				reply = "Unknown com.github.ArthurSchiavom.old.error :png307:489925231536766976>";
		}
		channel.sendMessage(reply).queue();
	}
}
