package com.github.ArthurSchiavom.old.commands.user.admin.utilities.trigger;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;

public class Trigger extends CommandWithSubCommands {
	public Trigger() {
		super(Category.UTILITY
				, "Manage triggers for this server."
		);
		this.addName("Triggers");
		this.addName("Trigger");
		this.addName("Trig");
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.addSubCommand(new TriggerAdd(this));
		this.addSubCommand(new TriggerRemove(this));
		this.addSubCommand(new TriggerConsult(this));
		this.buildHelpMessage();
	}
}
