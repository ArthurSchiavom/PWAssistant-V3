package com.github.ArthurSchiavom.old.commands.user.regular.fun.randomNumber;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class RandomNumber extends CommandWithoutSubCommands {
	private static final int LOWER_LIMIT_DEFAULT = 0;
	private static final int HIGHER_LIMIT_DEFAULT = 10;
	public RandomNumber() {
		super(Category.FUN
				, "Get a random number"
				, "LowerLimit HigherLimit"
				, false);
		this.addName("RandomNumber");
		this.addName("RandomNum");
		this.addName("RandNum");
		this.addExample("", String.format("Get a random number between %d and %d", LOWER_LIMIT_DEFAULT, HIGHER_LIMIT_DEFAULT));
		this.addExample("10 250", "Get a random number between 10 and 250");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		boolean fail = false;
		int lowerLimit = LOWER_LIMIT_DEFAULT;
		int higherLimit = HIGHER_LIMIT_DEFAULT;
		String msg = event.getMessage().getContentDisplay();
		String args = this.extractArgumentsOnly(msg);
		if (args != null) {
			String[] argsSplit = args.split(" ");
			if (argsSplit.length < 2) {
				channel.sendMessage("Invalid range! You must specify the lower limit and the upper limit!").queue();
				fail = true;
			}
			else {
				try {
					lowerLimit = Integer.parseInt(argsSplit[0]);
					higherLimit = Integer.parseInt(argsSplit[1]);
					if (lowerLimit > higherLimit) {
						int temp = lowerLimit;
						lowerLimit = higherLimit;
						higherLimit = temp;
					}
				} catch (Exception e) {
					channel.sendMessage("Invalid range! You can only use numbers!").queue();
					fail = true;
				}
			}
		}

		if (!fail) {
			final int lowerLimitFinal = lowerLimit;
			final int higherLimitFinal = higherLimit;
			new Thread(() -> {
				Message message = channel.sendMessage("Roling the dices!...").complete();
				int number = lowerLimitFinal + (new Random().nextInt(higherLimitFinal-lowerLimitFinal+1));
				try {
					Thread.sleep(1000);
				} catch (Exception e) {/** Even if it failed, it doesn't matter much */}
				message.editMessage(number + "!").queue();
			}).start();
		}
	}
}
