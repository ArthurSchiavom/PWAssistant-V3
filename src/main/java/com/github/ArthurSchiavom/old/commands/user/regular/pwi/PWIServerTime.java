package com.github.ArthurSchiavom.old.commands.user.regular.pwi;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.Calendar;

public class PWIServerTime extends CommandWithoutSubCommands {
	public PWIServerTime() {
		super(Category.PWI
				, "Check the time in a given server"
				, "server"
				, false);
		this.addName("PWIServerTime");
		this.addName("ServerTime");
		this.addExample("dawnglory", "Displays the current time in Dawnglory.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
		if (args == null) {
			channel.sendMessage("You must specify the server!").queue();
			return;
		}

		PwiServer server = PwiServer.getServer(args);
		if (server == null) {
			channel.sendMessage("There is no server with that name.").queue();
			return;
		}

		Calendar time = server.getCurrentTime();
		String timeDisplay = Utils.calendarToTimeDisplay(time);
		String serverName = server.getName();
		channel.sendMessage("Current time in " + serverName + ": **" + timeDisplay + "**").queue();
	}
}
