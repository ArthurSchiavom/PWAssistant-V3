package com.github.ArthurSchiavom.old.commands.user.regular.pwi;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.roles.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.List;

public class PWIServer extends CommandWithoutSubCommands {
	public PWIServer() {
		super(Category.PWI
				, "Add or remove PWI server roles from yourself."
				, "Server1, Server2,..."
				, false);
		this.addName("PWIServer");
		this.addName("Server");
		Requirement[] requirements = {Requirement.MAIN_GUILD};
		this.getRequirementsManager().setRequirements(requirements);
		this.addExample("dawnglory", "If you have the role Dawnglory, I'll remove it from you and if you don't, I'll add it!");
		this.addExample("etherblade dawnglory tideswell", "If you previously had the role Etherblade, I would remove the role Etherblade and add Dawnglory and Tideswell.");
		this.buildHelpMessage();
	}

	@Override
	protected void runCommandActions(MessageReceivedEvent event) {
		List<Long> mentionedRolesIds = Roles.getPWIServerRolesMentionedIn(event.getMessage().getContentDisplay());
		if (mentionedRolesIds.isEmpty()) {
			event.getChannel().sendMessage("You need to specify the servers to add/remove.").queue();
			return;
		}

		Guild guild = event.getGuild();
		Member member = event.getMember();
		event.getChannel().sendMessage(Utils.batchToggleRoles(guild, member, mentionedRolesIds)).queue();
	}
}
