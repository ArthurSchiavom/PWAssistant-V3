package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.trigger;

import com.github.ArthurSchiavom.pwassistant.control.trigger.TriggerService;
import com.github.ArthurSchiavom.pwassistant.entity.Trigger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.List;

@ApplicationScoped
public class TriggerExecutor {
    @Inject
    TriggerService triggerService;

    public void applyTriggers(final long serverId, final MessageChannel channel, String userMessage) {
        final List<Trigger> serverTriggers = triggerService.getTriggersOfServer(serverId);
        if (serverTriggers.isEmpty()) {
            return;
        }

        userMessage = userMessage.toLowerCase();
        for (final Trigger trigger : serverTriggers) {
            if (userMessage.contains(trigger.getTriggerTextLowercase())) {
                channel.sendMessage(trigger.getReply()).queue(msg -> {}, err -> {/* probably lack of permissions */});
            }
        }
    }
}
