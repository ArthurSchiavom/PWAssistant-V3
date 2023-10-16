package com.github.ArthurSchiavom.old.commands.user.admin.moderation.clear;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class Clear extends CommandWithoutSubCommands {
	public Clear() {
		super(Category.UTILITY
				, "Deletes the previous X messages."
				, "numberOfMessages"
				, true);
		this.addName("Clear");
		this.addName("Clean");
		this.addName("Prune");
		this.addName("Purge");
		this.addExample("10", "Removes the last 10 messages in this channel.");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		Message cmdMsg = event.getMessage();
		String args = this.extractArgumentsOnly(cmdMsg.getContentDisplay());

		MessageChannel channel = event.getChannel();
		if (args == null) {
			channel.sendMessage("Please provide an amount of messages to delete.").queue();
			return;
		}

		int amount = 0;
		try {
			amount = Integer.parseInt(args);
		} catch (NumberFormatException e) {
			channel.sendMessage("Please provide an amount of messages to delete using only numbers.").queue();
			return;
		}

		if (amount < 1) {
			channel.sendMessage("You can only delete 1 or more messages.").queue();
			return;
		}

		channel.getHistoryBefore(cmdMsg, amount).queue(
				(h) -> {
					List<Message> msgs = new ArrayList<>();
					msgs.addAll(h.getRetrievedHistory());
					msgs.add(cmdMsg);
					if (msgs.size() == 1) {
						msgs.get(0).delete().queue();
						return;
					}
					else {
						try {
							channel.purgeMessages(msgs);
						} catch (Exception e) {
							channel.sendMessage("No permission to remove messages 1+ of the messages.").queue(null, e2 -> {});
						}
					}

				}
				, e1 -> channel.sendMessage("No permission to read past messages").queue(null, e2 -> {}));
	}
}
