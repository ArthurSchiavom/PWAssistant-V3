package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden;

import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@ApplicationScoped
public class HiddenCommandExecutor {

    @Inject
    @All
    List<HiddenCommand> hiddenCommandList;

    @Inject
    GlobalConfig globalConfig;

    public void process(final MessageReceivedEvent event) {
        if (!globalConfig.getBotOwnerId().equals(event.getAuthor().getIdLong()) || !event.isFromGuild()) {
            return;
        }
        hiddenCommandList.forEach(c -> c.process(event));
    }
}
