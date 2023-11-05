package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.command;


import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.SplitUtil;

import java.util.ArrayList;
import java.util.Arrays;
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
            List<Member> guildMembers = guild.getMembers();
            int nMembersNotOflline = filterMembersByStatus(guildMembers, true, OnlineStatus.ONLINE
                    , OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE
                    , OnlineStatus.INVISIBLE, OnlineStatus.UNKNOWN).size();
            final Member owner = guild.retrieveOwner().complete();
            sb.append("\n\n**").append(guild.getName()).append("** is owned by **")
                    .append(owner.getUser().getGlobalName()).append(" (")
                    .append(owner.getUser().getName()).append(")**, has **")
                    .append(guildMembers.size()).append("** members (**")
                    .append(nMembersNotOflline).append("** non-bots online)")
                    .append(" and it's ID is ")
                    .append(guild.getId()).append(".");
        }
        return sb.toString();
    }

    private String buildCountDisplay(JDA jda) {
        StringBuilder sb = new StringBuilder();
        List<Guild> guilds = jda.getGuilds();
        List<User> users = jda.getUsers();
        int nMembersNotOffline = getAllMembersOfStatus(false, true, OnlineStatus.ONLINE
                , OnlineStatus.DO_NOT_DISTURB, OnlineStatus.IDLE
                , OnlineStatus.INVISIBLE, OnlineStatus.UNKNOWN).size();
        sb.append("I'm in **").append(guilds.size()).append("** guilds, serving **")
                .append(users.size()).append("** users (**").append(nMembersNotOffline).append("** non-bots online)!");
        return sb.toString();
    }


    public List<Member> getAllMembersOfStatus(boolean allowRepeatedUsers, boolean ignoreBots, OnlineStatus... validOnlineStatuses) {
        List<Guild> guilds = bot.getJda().getGuilds();
        List<Member> result = new ArrayList<>();

        for (Guild guild : guilds) {
            List<Member> membersToAdd = filterMembersByStatus(guild.getMembers(), ignoreBots, validOnlineStatuses);

            for (Member memberToAdd : membersToAdd) {
                boolean canAdd = true;

                if (!allowRepeatedUsers) {
                    for (Member member : result) {
                        if (memberToAdd.getIdLong() == member.getIdLong()) {
                            canAdd = false;
                            break;
                        }
                    }
                }

                if (canAdd)
                    result.add(memberToAdd);
            }
        }

        return result;
    }

    /**
     * Finds all members that are not offline from a list.
     *
     * @param members The list of members.
     * @return all members from the list that are not offline.
     */
    public List<Member> filterMembersByStatus(List<Member> members, boolean ignoreBots, OnlineStatus... validOnlineStatuses) {
        List<Member> filteredMembers = new ArrayList<>();
        List<OnlineStatus> validOnlineStatusesList = Arrays.asList(validOnlineStatuses);
        for (Member member : members) {
            boolean canAdd = true;

            if (ignoreBots && member.getUser().isBot())
                canAdd = false;

            if (canAdd && !validOnlineStatusesList.contains(member.getOnlineStatus()))
                canAdd = false;

            if (canAdd)
                filteredMembers.add(member);
        }
        return filteredMembers;
    }
}
