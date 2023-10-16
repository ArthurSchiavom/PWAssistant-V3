package com.github.ArthurSchiavom.old.commands.user.regular.pwi;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems.PWIItems;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class PWIItemInfo extends CommandWithoutSubCommands {
	public PWIItemInfo() {
		super(Category.PWI
				, "Search com.github.ArthurSchiavom.old.information about an item"
				, "Item name"
				, true);
		this.addName("PWIItem");
		this.addName("PWIItemInfo");
		this.addName("ItemInfo");
		this.addName("Item");
		this.addExample("Astral Ballad", "Provides links to the Astral Ballad and it's mold's pages on PWDatabase.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		if (args == null) {
			channel.sendMessage("You must provide the name of the item to search for!").queue();
			return;
		}

		List<com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems.PWIItem> pwiItems = PWIItems.getMatchingItems(args);
		if (pwiItems.size() > 10) {
			channel.sendMessage("More than 10 results! Try again but be more specific.").queue();
		}
		else if(pwiItems.size() < 1) {
			channel.sendMessage("No items found by **" + args + "**").queue();
		}
		else {
			StringBuilder resultSB = new StringBuilder();
			for (com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems.PWIItem item : pwiItems) {
				resultSB.append("\n__").append(item.getName()).append("__: <")
						.append(item.getInfoLink()).append(">");
			}
			channel.sendMessage(resultSB.toString()).queue();
		}
	}
}
