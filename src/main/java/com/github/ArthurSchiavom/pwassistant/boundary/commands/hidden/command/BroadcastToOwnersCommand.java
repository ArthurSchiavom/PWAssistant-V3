package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.command;

import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommand;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BroadcastToOwnersCommand implements HiddenCommand {


    private static final String NAME = "pw broadcast ";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean process(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(NAME)) {
            return false;
        }

        final List<Long> ownersMessagedIds = new ArrayList<>();
        final MessageChannel channel = event.getChannel();
        final String args = event.getMessage().getContentRaw().replaceFirst(NAME, "");
        if (args.length() > 1800 || args.isBlank()) {
            channel.sendMessage("No message specified or message too long.").queue();
            return false;
        }

        final String messageString = "`This message is only being sent to server owners`\n\n" + args;
        final MessageCreateData message = MessageCreateData.fromContent(messageString);

        final List<Guild> guilds = Bot.getJdaInstance().getGuilds();
        int successCount = 0;
        for (final Guild guild : guilds) {
            final User owner = guild.getOwner().getUser();
            final Long ownerId = owner.getIdLong();
            if (!ownersMessagedIds.contains(ownerId)) {
                ownersMessagedIds.add(ownerId);
                try {
                    owner.openPrivateChannel().complete().sendMessage(message).complete();
                    successCount++;
                } catch (Exception e) {
                }
            }
        }
        channel.sendMessage("Successfully messaged **" + successCount + "** server owners.").queue();

        return true;
    }
}
