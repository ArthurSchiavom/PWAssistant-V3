package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi.server;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices.PwiServerChoices;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.validations.Validations;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.ChoiceUtils;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.RoleUtils;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class GetServerRoleCommand implements SlashCommand {
    private static final String NAME = "role";
    private static final String DESCRIPTION = "Get or remove a PWI server role!";

    private static final String OPTION_SERVER_NAME = "server-name";

    @Inject
    Validations validations;
    @Inject
    GlobalConfig config;
    final PwiServerChoices pwiServerChoices = new PwiServerChoices();

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.SERVER, NAME),
                DESCRIPTION,
                true,
                List.of(new OptionData(OptionType.STRING, OPTION_SERVER_NAME, "ET, TT, TI or DA", true, true)),
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (!validations.usedInMainServer(event)) {
            event.reply("This command is only usable in my home server, PWI Kingdom.").queue();
            return;
        }
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the server in the command when you use it.").queue();
            return;
        }

        final PwiServer server = pwiServerChoices.getOptionObjectFromPayload(event, OPTION_SERVER_NAME);
        if (server == null) {
            event.reply("Could not identify the server you specified, please choose Etherblade, Twilight Temple, Tideswell or Dawnglory.").queue();
            return;
        }

        RoleUtils.toggleRole(event.getGuild(), server.getId(config), event.getMember(),
                h -> event.reply("You now have the role " + server.getName() + "!").queue(),
                h -> event.reply("You no longer have the role " + server.getName() + "!").queue(),
                t -> event.reply("Failed to add/remove the role " + server.getName() + " from you. Please mention admins.").queue());
    }

    @Override
    public List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        final String userInput = ChoiceUtils.getChoiceAsString(event, OPTION_SERVER_NAME);
        return pwiServerChoices.getAutocompleteChoices(userInput);
    }
}
