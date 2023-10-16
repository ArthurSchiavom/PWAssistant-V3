package com.github.ArthurSchiavom.old.commands.user.admin.moderation.warning;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;

public class Warning extends CommandWithSubCommands {
	public Warning() {
		super(Category.UTILITY, "Manage user users");
		this.addName("Warning");
		this.addName("W");

	}
}
