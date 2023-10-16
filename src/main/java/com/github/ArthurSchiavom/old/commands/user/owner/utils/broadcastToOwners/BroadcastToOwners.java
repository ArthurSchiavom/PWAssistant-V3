package com.github.ArthurSchiavom.old.commands.user.owner.utils.broadcastToOwners;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.List;

public class BroadcastToOwners extends CommandWithoutSubCommands {

	public BroadcastToOwners() {
		super(Category.UTILITY
				, "Broadcasts a message to all server owners that the bot is in. A header is included saying that only server owners are receiving the message."
				, "Message"
				, true);
		this.addName("BroadcastToOwners");
		this.getRequirementsManager().addRequirement(Requirement.OWNER);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		List<Long> ownersMessagedIds = new ArrayList<>();
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentRaw());
		if (args == null || args.length() > 1800) {
			channel.sendMessage("No message specified or message too long.").queue();
			return;
		}

		String messageString = "`This message is only being sent to server owners`\n\n" + args;
		MessageCreateData message = MessageCreateData.fromContent(messageString);

		List<Guild> guilds = Bot.getJdaInstance().getGuilds();
		int successCount = 0;
		for (Guild guild : guilds) {
			User owner = guild.getOwner().getUser();
			Long ownerId = owner.getIdLong();
			if (!ownersMessagedIds.contains(ownerId)) {
				ownersMessagedIds.add(ownerId);
				try {
					owner.openPrivateChannel().complete().sendMessage(message).complete();
					successCount++;
				} catch (Exception e) {
				}
			}
		}
		channel.sendMessage("Successfully messaged **" + successCount + "** server owners.").queue();
	}
}
