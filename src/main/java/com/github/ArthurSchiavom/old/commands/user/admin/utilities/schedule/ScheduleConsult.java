package com.github.ArthurSchiavom.old.commands.user.admin.utilities.schedule;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingScheduler;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingSchedulerRegister;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ScheduleConsult extends CommandWithoutSubCommands {
	public ScheduleConsult(Command superCommand) {
		super(superCommand.getCategory()
				, "Consult existing schedules."
				, "ScheduleName"
				, false
				, superCommand);
		this.addName("Consult");
		this.addName("C");
		this.addExample("", "See a short-format com.github.ArthurSchiavom.old.information about all schedules.");
		this.addExample("EventReminder", "See com.github.ArthurSchiavom.old.information about the schedule EventReminder.");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();

		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		String guildId = event.getGuild().getId();
		if (args == null) {
			channel.sendMessage(buildFullList(guildId)).queue();
		}
		else {
			MessagingScheduler messagingScheduler = MessagingSchedulerRegister.getSchedule(guildId, args);

			if (messagingScheduler == null)
				channel.sendMessage("There is no schedule with that name. <:png98:489925231285239830>").queue();
			else
				channel.sendMessage(messagingScheduler.buildFullVisualRepresentation()).queue();
		}
	}

	private String buildFullList(String guildId) {
		List<MessagingScheduler> guildSchedulers = MessagingSchedulerRegister.getGuildSchedules(guildId);
		if (guildSchedulers.isEmpty()) {
			return "No schedules were found in this server <:ffconfused:489925231285239830>";
		}

		StringBuilder sb = new StringBuilder();
		for (MessagingScheduler scheduler : guildSchedulers) {
			sb.append("\n\n").append(scheduler.buildShortVisualRepresentation());
		}
		return sb.toString();
	}
}
