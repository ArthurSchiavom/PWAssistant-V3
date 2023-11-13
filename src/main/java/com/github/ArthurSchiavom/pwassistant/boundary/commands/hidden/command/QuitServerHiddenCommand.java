package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.command;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommand;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@ApplicationScoped
public class QuitServerHiddenCommand implements HiddenCommand {


    private static final String NAME = "pw quitserver ";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean process(MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(NAME)) {
            return false;
        }

        MessageChannel channel = event.getChannel();
        final String[] args = event.getMessage().getContentRaw().split(" ");
        if (args.length < 3) {
            channel.sendMessage("Where did you forget the server ID? You need to give it to me.").queue();
            return false;
        }
        final String serverId = args[2];
        try {
            final Guild guild = event.getJDA().getGuildById(serverId);
            if (guild == null) {
                channel.sendMessage("No server with ID " + serverId).queue();
                return false;
            }
            final String guildName = guild.getName();
            guild.leave().complete();
            channel.sendMessage("Left **" + guildName + "**").queue();
        } catch (Exception e) {
            channel.sendMessage("Failed to leave the guild. Is the ID correct?").queue();
        }

        return true;
    }
}
