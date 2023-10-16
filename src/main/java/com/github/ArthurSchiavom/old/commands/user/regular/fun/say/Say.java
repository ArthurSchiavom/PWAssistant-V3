package com.github.ArthurSchiavom.old.commands.user.regular.fun.say;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Say extends CommandWithoutSubCommands {
	public Say() {
		super(Category.FUN
				, "Ask me to repeat something!"
				, "What to repeat"
				, false);
		this.addName("Say");
		this.addExample("Hello!", "I'll say `Hello!`.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		String args = this.extractArgumentsOnly(msg.getContentRaw());
		msg.delete().queue();
		MessageChannel channel = event.getChannel();
		if (args == null)
			channel.sendMessage("You need to tell me something to repeat!").queue();
		else
			channel.sendMessage(args).queue();
	}
}
