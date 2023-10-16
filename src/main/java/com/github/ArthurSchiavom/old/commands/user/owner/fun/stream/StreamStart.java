package com.github.ArthurSchiavom.old.commands.user.owner.fun.stream;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.Command;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Misc;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StreamStart extends CommandWithoutSubCommands {

    public StreamStart(Command superCommand) {
        super(Category.FUN, "Start the stream", "StreamName", false, superCommand);
        this.addName("Start");
        this.addName("Begin");
        this.getRequirementsManager().addRequirement(Requirement.OWNER);
        this.buildHelpMessage();
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        String streamName = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
        if (streamName == null)
            event.getChannel().sendMessage("You need to specify the stream name").queue();
        else
            Bot.getJdaInstance().getPresence().setActivity(Activity.streaming(streamName, Misc.streamUrl));
    }
}
