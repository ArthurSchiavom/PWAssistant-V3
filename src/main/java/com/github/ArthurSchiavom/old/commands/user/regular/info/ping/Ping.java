package com.github.ArthurSchiavom.old.commands.user.regular.info.ping;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping extends CommandWithoutSubCommands {
	public Ping() {
		super(Category.INFORMATION
				, "Find out the bot ping with the Discord server"
				, null
				, true);
		this.addName("Ping");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		JDA jda = event.getJDA();
		Message msg = channel.sendMessage("Pong!").complete();
		String result = String.format("Pong!" +
				"\n**%dms** directly • **%dms** for requests.", jda.getGatewayPing(), jda.getRestPing().complete().longValue());
		msg.editMessage(result).queue();
	}
}
