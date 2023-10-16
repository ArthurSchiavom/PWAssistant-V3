package com.github.ArthurSchiavom.old.commands.user.admin.utilities.countdown;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.error.Reporter;
import com.github.ArthurSchiavom.old.information.clocks.ClockRegister;
import com.github.ArthurSchiavom.old.information.clocks.CountdownClock;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Calendar;

public class Countdown extends CommandWithoutSubCommands {
	public static final int MAX_DURATION_DAY = 370;
	public static final int MAX_DURATION_MIN = 532800;

	public Countdown() {
		super(Category.UTILITY
				, "Create a countdown clock"
				, "MinutesToEnd"
				, true);
		this.addName("Countdown");
		this.addName("CountdownClock");
		this.addExample("65", "Creates a countdown clock that ends after 65 minutes (1 hour and 5 minutes)");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel();
		Message msg = event.getMessage();
		String args = this.extractArgumentsOnly(msg.getContentRaw());
		try {msg.delete().complete();} catch (Exception e) {}
		if (args == null) {
			channel.sendMessage("You must tell me how many minutes are left until the countdown ends.").queue();
			return;
		}

		int duration;
		try {
			duration = Integer.parseInt(args);
		} catch (NumberFormatException e) {
			channel.sendMessage("The amount of minutes left until the end must be specified with only numbers.").queue();
			return;
		}

		if (duration < 1) {
			channel.sendMessage("The countdown duration must be at least 1 minute.").queue();
			return;
		}

		if (duration > MAX_DURATION_MIN) {
			channel.sendMessage("Countdowns can't last more than " + MAX_DURATION_DAY + " days.").queue();
			return;
		}

		Calendar currentTime = Calendar.getInstance();
		currentTime.add(Calendar.MINUTE, duration);
		String guildId = event.getGuild().getId();
		String channelId = channel.getId();
		MessageEmbed loadingEmbed = new EmbedBuilder().setTitle("【Ｌｏａｄｉｎｇ】")
				.setDescription("This can take a few seconds.").build();
		Message loadingMsg = channel.sendMessageEmbeds(loadingEmbed).complete();
		String loadingMsgId = loadingMsg.getId();
		CountdownClock clock = new CountdownClock(currentTime.toInstant()
				, guildId
				, channelId
				, loadingMsgId);
		try {
			boolean success = ClockRegister.getInstance().register(clock, true);

			if (!success) {
				channel.sendMessage("The limit of " + ClockRegister.MAX_CLOCKS_PER_GUILD + " clocks per guild has been reached.").complete();
				loadingMsg.delete().complete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			channel.sendMessage("Failed to add clock. Please try again.").queue();
			Reporter.report("Failed to add clock. Check logs");
		}
	}
}
