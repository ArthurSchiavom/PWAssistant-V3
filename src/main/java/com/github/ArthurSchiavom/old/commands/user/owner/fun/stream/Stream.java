package com.github.ArthurSchiavom.old.commands.user.owner.fun.stream;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;

public class Stream extends CommandWithSubCommands {

    public Stream() {
        super(Category.FUN, "Change stream status");
        this.addName("Stream");
        this.addSubCommand(new StreamStart(this));
        this.addSubCommand(new StreamEnd(this));
        this.getRequirementsManager().addRequirement(Requirement.OWNER);
        this.buildHelpMessage();
    }
}
