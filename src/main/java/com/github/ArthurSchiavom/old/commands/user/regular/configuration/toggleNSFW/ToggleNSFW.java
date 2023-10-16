package com.github.ArthurSchiavom.old.commands.user.regular.configuration.toggleNSFW;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.roles.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ToggleNSFW extends CommandWithoutSubCommands {
	public ToggleNSFW() {
		super(Category.CONFIGURATION
				, "Toggle NSFW channel visiblity."
				, null
				, false);
		this.addName("ToggleNSFW");
		this.getRequirementsManager().addRequirement(Requirement.MAIN_GUILD);
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		event.getMessage().delete().reason("People don't like others to see when they make the porn channel visible.").queue();

		Member member = event.getMember();
		List<Role> memberRoles = member.getRoles();
		Role nsfwRole = Roles.retrieveJdaNsfwRole();
		long nsfwRoleId = nsfwRole.getIdLong();
		Guild guild = event.getGuild();
		MessageChannel channel = event.getChannel();

		boolean hasNsfwRole = false;
		for (Role role : memberRoles) {
			if (role.getIdLong() == nsfwRoleId) {
				hasNsfwRole = true;
				break;
			}
		}

		String resultMessage;
		if (hasNsfwRole) {
			guild.removeRoleFromMember(member, nsfwRole).queue();
			resultMessage = "Lewd content hidden! \uD83D\uDE07";

		}
		else {
			guild.addRoleToMember(member, nsfwRole).queue();
			resultMessage = "You have unlocked all the lewd material! \uD83D\uDE33";
		}
		channel.sendMessage(resultMessage).queue(msg -> {
			try {Thread.sleep(5000);} catch (InterruptedException e) {}
			msg.delete().reason("People don't like others to see when they make the porn channel visible.").delay(5L, TimeUnit.SECONDS).queue();
		});
	}
}
