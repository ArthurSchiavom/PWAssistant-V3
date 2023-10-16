package com.github.ArthurSchiavom.old.commands.user.admin.utilities.schedule;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;

public class Schedule extends CommandWithSubCommands {
	public Schedule() {
		super(Category.UTILITY
				, "Manage message schedules."
		);
		this.addName("Schedule");
		this.addSubCommand(new ScheduleAdd(this));
		this.addSubCommand(new ScheduleRemove(this));
		this.addSubCommand(new ScheduleConsult(this));
		this.getRequirementsManager().addRequirement(Requirement.ADMIN);
		this.buildHelpMessage();
	}
}
