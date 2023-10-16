package com.github.ArthurSchiavom.old.events.operators;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;

import java.util.ArrayList;
import java.util.List;

public class GuildMessageDeleteEventOperator {
    private static List<Message> messageCache = new ArrayList<>();
    private static List<Long> messageIdCache = new ArrayList<>();

    public void processEvent(MessageDeleteEvent event) {
        if (event.getGuild().getIdLong() != 251460250115375114L)
            return;

        Long id = event.getMessageIdLong();
        int index = messageIdCache.indexOf(id);
        if (index == -1)
            return;

        Message msg = messageCache.get(index);

        EmbedBuilder eb = new EmbedBuilder();
        Member author = msg.getMember();
        Embeds.configDefaultEmbedColor(eb);
        eb.setDescription(msg.getContentRaw());
        String mention = author.getAsMention();

        for (Role role : author.getRoles()) {
            if (role.getIdLong() == 728245545180856420L)
                mention = author.getEffectiveName();
        }

        event.getJDA().getTextChannelById(327838533853380608L)
                .sendMessage(String.format("Message by %s (%s (nickname), %s, %s) on channel <#%s> was Deleted",
                        mention, author.getNickname(),
                        author.getUser().getAsTag(), author.getId(),
                        msg.getChannel().getId()))
                .setEmbeds(eb.build())
                .queue();
    }

    public static void storeMessage(Message msg) {
        messageCache.add(msg);
        messageIdCache.add(msg.getIdLong());
        if (messageCache.size() > 2000) {
            messageCache.remove(0);
            messageIdCache.remove(0);
        }
    }
}
