package com.github.ArthurSchiavom.old.commands.user.regular.configuration.toggle.free.games.notification;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import com.github.ArthurSchiavom.old.commands.base.Requirement;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.roles.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ToggleFreeGamesNotification extends CommandWithoutSubCommands {
    public ToggleFreeGamesNotification() {
        super(Category.CONFIGURATION
                , "Toggle free games notifications."
                , null
                , false);
        this.addName("FreeGamesNotifications");
        this.addName("FreeGamesNotification");
        this.addName("FreeGameNotifications");
        this.addName("FreeGameNotification");
        this.getRequirementsManager().addRequirement(Requirement.MAIN_GUILD);
        this.buildHelpMessage();
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        Member member = event.getMember();
        List<Long> wrapper = new ArrayList<>();
        wrapper.add(Roles.retrieveJdaFreeGamesRole().getIdLong());
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        String reply = Utils.batchToggleRoles(guild, member, wrapper);
        channel.sendMessage(reply).queue();
    }
}
