package com.github.ArthurSchiavom.old.commands.user.owner.fun.stream;

import com.github.ArthurSchiavom.lifecycle.start.Bootstrap;
import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StreamEnd extends CommandWithoutSubCommands {
    public StreamEnd(Command superCommand) {
        super(Category.FUN, "End the stream", null, false, superCommand);
        this.addName("End");
        this.addName("Stop");
        this.getRequirementsManager().addRequirement(Requirement.OWNER);
        this.buildHelpMessage();
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        Bootstrap.setDefaultPlaying();
    }
}
