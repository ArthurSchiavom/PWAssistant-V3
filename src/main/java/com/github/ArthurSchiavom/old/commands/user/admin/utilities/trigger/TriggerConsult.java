package com.github.ArthurSchiavom.old.commands.user.admin.utilities.trigger;

import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import com.github.ArthurSchiavom.old.information.triggers.Trigger;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class TriggerConsult extends CommandWithoutSubCommands {
	private final static int MAX_REPLY_CHARS_DISPLAY = 50;

	public TriggerConsult(Command superCommand) {
		super(superCommand.getCategory()
				, "Remove a trigger."
				, "TriggerName"
				, true
				, superCommand);
		this.addName("Consult");
		this.addName("List");
		this.addName("C");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		List<Trigger> triggers = TriggerRegister.getInstance().getGuildTriggers(event.getGuild().getIdLong());
		StringBuilder triggerListDisplay = new StringBuilder();
		if (triggers != null && !triggers.isEmpty()) {
			for (Trigger trigger : triggers) {
				String fullReply = trigger.getReply();
				String replyDisplay;
				try {
					replyDisplay = fullReply.substring(0, MAX_REPLY_CHARS_DISPLAY) + "`...`";
				} catch (IndexOutOfBoundsException e) {
					replyDisplay = fullReply;
				}
				triggerListDisplay.append("\n\n**").append(trigger.getTriggerLowerCase())
						.append("** -> ").append(replyDisplay.replace("*", "")
						.replace("`", "")
						.replace("_", "")
						.replace("~", "")); // The replace is done because those characters can mess up the formatting
			}
		}
		else {
			triggerListDisplay.append("Searched everywhere with beeps and boops magic but didn't find any triggers in this server.");
		}
		EmbedBuilder eb = new EmbedBuilder().setTitle("Triggers").setDescription(triggerListDisplay.toString()).setFooter("Did you know that triggers can be used to make custom com.github.ArthurSchiavom.old.commands?!");
		Embeds.configDefaultEmbedColor(eb);
		event.getChannel().sendMessageEmbeds(eb.build()).queue();
	}
}
