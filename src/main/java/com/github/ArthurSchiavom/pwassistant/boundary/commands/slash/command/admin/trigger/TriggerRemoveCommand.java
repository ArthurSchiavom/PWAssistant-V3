package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.trigger;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.control.trigger.TriggerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class TriggerRemoveCommand implements SlashCommand {
    private static final String NAME = "remove";
    private static final String DESCRIPTION = "Remove a trigger. (admin only)";
    private static final String OPTION_NAME_TRIGGER = "trigger";
    private static final String OPTION_DESCRIPTION_TRIGGER = "trigger";

    @Inject
    TriggerService triggerService;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.TRIGGER, null, NAME),
                DESCRIPTION,
                List.of(InteractionContextType.GUILD),
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_TRIGGER, OPTION_DESCRIPTION_TRIGGER, true, false)),
                SlashCommandCategory.ADMIN,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the name of the trigger to remove").setEphemeral(true).queue();
            return;
        }

        final String triggerName = event.getOption(OPTION_NAME_TRIGGER).getAsString();
        final boolean deleted = triggerService.deleteTriggerByServerAndTriggerName(event.getGuild().getIdLong(), triggerName);
        if (deleted) {
            event.reply("trigger " + triggerName + " removed").queue();
        } else {
            event.reply("no trigger of name " + triggerName + " found").queue();
        }
    }
}
