package com.github.ArthurSchiavom.old.commands.user.owner.utils.manageServers;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;

public class ManageServers extends CommandWithSubCommands {
	public ManageServers() {
		super(Category.UTILITY
				, "View and modify the bot presence in servers."
		);
		this.addName("ManageServers");
		this.addName("MS");
		this.addSubCommand(new Status(this));
		this.addSubCommand(new Quit(this));
		this.getRequirementsManager().addRequirement(Requirement.OWNER);
		this.buildHelpMessage();
	}
}
