package com.github.ArthurSchiavom.pwassistant.boundary.welcome;

import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

@ApplicationScoped
public class PWIKingdomWelcome {

    @Inject
    GlobalConfig globalConfig;

    public static final String WELCOME_MSG_TEMPLATE = "Welcome to PWI Kingdom **%s**! Make sure to read <#617691793315725317> to learn the rules and other useful information! <:ttCute:288020411126644736>";

    public void processGuildMemberJoinEvent(final GuildMemberJoinEvent event) {
        if (!globalConfig.getMainServerId().equals(event.getGuild().getIdLong())) {
            return;
        }

        event.getJDA().getTextChannelById(globalConfig.getWelcomeChannelId())
                .sendMessage(String.format(WELCOME_MSG_TEMPLATE, event.getMember().getEffectiveName()))
                .queue();
    }
}
