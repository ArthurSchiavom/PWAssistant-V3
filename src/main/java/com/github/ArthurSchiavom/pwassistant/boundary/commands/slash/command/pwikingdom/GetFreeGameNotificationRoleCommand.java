package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwikingdom;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.validations.Validations;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.RoleUtils;
import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig.MAIN_SERVER_INVITE_URL;

@ApplicationScoped
public class GetFreeGameNotificationRoleCommand implements SlashCommand {
    private static final String NAME = "role";
    private static final String DESCRIPTION = "Get, or remove, the role that notifies you when AAA games are temporarily free to keep.";

    @Inject
    Validations validations;
    @Inject
    GlobalConfig globalConfig;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI_KINGDOM, SlashCommandNames.FREE_GAMES, NAME),
                DESCRIPTION,
                false,
                null,
                SlashCommandCategory.INFO);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (!validations.usedInMainServer(event)) {
            event.reply("This command is only usable in my home server, [PWI Kingdom](" + MAIN_SERVER_INVITE_URL + ")! Feel free to join if you want to get the notifications ^^").queue();
            return;
        }

        RoleUtils.toggleRole(event.getGuild(), globalConfig.getRoleIdFreeGames(), event.getMember(),
                h -> event.reply("You will now be pinged when AAA, or very popular games, are on promotion as free to keep!").queue(),
                h -> event.reply("You will no longer be pinged when AAA, or very popular games, are on promotion as free to keep!").queue(),
                t -> event.reply("Failed to add/remove the role from you. Please mention admins.").queue());
    }
}
