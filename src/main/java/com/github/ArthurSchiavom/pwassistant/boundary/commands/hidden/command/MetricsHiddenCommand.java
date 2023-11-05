package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.command;


import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.SplitUtil;

import java.util.List;

import static net.dv8tion.jda.api.entities.Message.MAX_CONTENT_LENGTH;

@ApplicationScoped
public class MetricsHiddenCommand implements HiddenCommand {

    private static final String NAME = "pw metrics count / pw metrics full";
    private static final String COUNT_SUBCOMMAND = "pw metrics count";
    private static final String FULL_SUBCOMMAND = "pw metrics full";

    @Inject
    Bot bot;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean process(MessageReceivedEvent event) {
        final String message = event.getMessage().getContentRaw().toLowerCase();
        String reply;
        if (message.equals(COUNT_SUBCOMMAND)) {
            reply = buildCountDisplay(bot.getJda());
        }
        else if (message.equals(FULL_SUBCOMMAND)) {
            reply = buildFullDisplay(bot.getJda());
        } else {
            return false;
        }

        List<String> messages = SplitUtil.split(reply, MAX_CONTENT_LENGTH, true, SplitUtil.Strategy.NEWLINE);
        for (String messageContent : messages) {
            event.getChannel().sendMessage(messageContent).queue();
        }

        return true;
    }


    private String buildFullDisplay(JDA jda) {
        StringBuilder sb = new StringBuilder();
        List<Guild> guilds = jda.getGuilds();
        for (Guild guild : guilds) {
            final int memberCount = guild.getMemberCount();
            final Member owner = guild.retrieveOwner().complete();
            sb.append("\n\n**").append(guild.getName()).append("** is owned by **")
                    .append(owner.getUser().getGlobalName()).append(" (")
                    .append(owner.getUser().getName()).append(")**, has **")
                    .append(memberCount).append("** members")
                    .append(" and it's ID is ")
                    .append(guild.getId()).append(".");
        }
        return sb.toString();
    }

    private String buildCountDisplay(JDA jda) {
        StringBuilder sb = new StringBuilder();
        final List<Guild> guilds = jda.getGuilds();
        final int userCount = guilds.stream().map(Guild::getMemberCount).reduce(0, Integer::sum);
        sb.append("I'm in **").append(guilds.size()).append("** guilds, serving **")
                .append(userCount).append("** users!");
        return sb.toString();
    }
}
