package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi.classes;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices.PwiClassChoices;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.validations.Validations;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.ChoiceUtils;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.RoleUtils;
import com.github.ArthurSchiavom.pwassistant.entity.PwiClass;
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
public class GetClassRoleCommand implements SlashCommand {
    private static final String NAME = "role";
    private static final String DESCRIPTION = "Get or remove a PWI class role!";

    private static final String OPTION_CLASS_NAME = "class-name";

    @Inject
    Validations validations;
    @Inject
    GlobalConfig config;
    final PwiClassChoices pwiServerChoices = new PwiClassChoices();

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.CLASS, NAME),
                DESCRIPTION,
                true,
                List.of(new OptionData(OptionType.STRING, OPTION_CLASS_NAME, "Your class, such as Blademaster, Barbarian, Cleric, etc.", true, true)),
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (!validations.usedInMainServer(event)) {
            event.reply("This command is only usable in my home server, PWI Kingdom.").queue();
            return;
        }
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the class in the command when you use it.").queue();
            return;
        }

        final PwiClass pwiClass = pwiServerChoices.getOptionObjectFromPayload(event, OPTION_CLASS_NAME);
        if (pwiClass == null) {
            event.reply("Could not identify the PWI class you specified. Please specify a PWI class such as Blademaster, Cleric, Barbarian, etc.").queue();
            return;
        }

        RoleUtils.toggleRole(event.getGuild(), pwiClass.getRoleId(), event.getMember(),
                h -> event.reply("You now have the role " + pwiClass.getName() + "!").queue(),
                h -> event.reply("You no longer have the role " + pwiClass.getName() + "!").queue(),
                t -> event.reply("Failed to add/remove the role " + pwiClass.getName() + " from you. Please mention admins.").queue());
    }

    @Override
    public List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        final String userInput = ChoiceUtils.getChoiceAsString(event, OPTION_CLASS_NAME);
        return pwiServerChoices.getAutocompleteChoices(userInput);
    }
}
