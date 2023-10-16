package com.github.ArthurSchiavom.old.commands.user.regular.pwi;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.URL;
import java.util.Scanner;

public class Codes extends CommandWithoutSubCommands {
	private final static String PASTEBIN_URL = "https://pastebin.com/raw/r4yp66Zt";

	public Codes() {
		super(Category.PWI
				, "List of active codes!"
				, null
				, false);
		this.addName("Codes");
		this.addName("Code");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		StringBuilder finalMessageSB = new StringBuilder();

		URL PastebinURL;
		try {
			PastebinURL = new URL(PASTEBIN_URL);

			Scanner scanner = new Scanner(PastebinURL.openStream(), "utf-8");

			finalMessageSB.append(scanner.nextLine());
			while(scanner.hasNext())
				finalMessageSB.append("\n" + scanner.nextLine());

			scanner.close();
		} catch (Exception e) {
			event.getChannel().sendMessage("Failed to retrieve the list of codes.").queue();
		}

		event.getChannel().sendMessage( finalMessageSB.toString() ).queue();
	}
}
