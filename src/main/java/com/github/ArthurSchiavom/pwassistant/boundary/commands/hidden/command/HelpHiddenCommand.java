package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.command;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommand;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@ApplicationScoped
public class HelpHiddenCommand implements HiddenCommand {

    @Inject
    @All
    List<HiddenCommand> hiddenCommandList;

    private static final String NAME = "pw help";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean process(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().equals(NAME)) {
            return false;
        }

        final StringBuilder sb = new StringBuilder();
        hiddenCommandList.forEach(c -> sb.append("\n• ").append(c.getName()));
        event.getChannel().sendMessage(sb.toString()).queue();

        return true;
    }
}
