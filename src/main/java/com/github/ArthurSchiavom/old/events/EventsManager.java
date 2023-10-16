package com.github.ArthurSchiavom.old.events;

import com.github.ArthurSchiavom.old.events.operators.GuildMessageDeleteEventOperator;
import com.github.ArthurSchiavom.old.events.operators.GuildMessageEventOperator;
import com.github.ArthurSchiavom.old.events.operators.MemberJoinedEventOperator;
import com.github.ArthurSchiavom.old.events.operators.PrivateMessageEventOperator;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Manages received JDA com.github.ArthurSchiavom.old.events.
 */
public class EventsManager extends ListenerAdapter {

    private GuildMessageEventOperator guildMessageEventOperator = new GuildMessageEventOperator();
    private PrivateMessageEventOperator privateMessageEventOperator = new PrivateMessageEventOperator();
    private GuildMessageDeleteEventOperator guildMessageDeleteEventOperator = new GuildMessageDeleteEventOperator();
    private MemberJoinedEventOperator memberJoinedEventOperator = new MemberJoinedEventOperator();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannelType() == ChannelType.PRIVATE) {
            privateMessageEventOperator.processMessageReceived(event);
        } else if (event.getChannelType() == ChannelType.TEXT) {
            guildMessageEventOperator.processMessageReceived(event);
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        memberJoinedEventOperator.processMemberJoinedEvent(event);
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        if (event.isFromGuild()) {
            guildMessageDeleteEventOperator.processEvent(event);
        }
    }
}
