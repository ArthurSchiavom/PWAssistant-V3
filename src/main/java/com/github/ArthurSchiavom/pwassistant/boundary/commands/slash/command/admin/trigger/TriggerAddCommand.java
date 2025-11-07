package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.trigger;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.trigger.AddTriggerQuestionnaire;
import com.github.ArthurSchiavom.pwassistant.control.trigger.TriggerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

import java.util.List;

@ApplicationScoped
public class TriggerAddCommand implements SlashCommand {
    private static final String NAME = "add";
    private static final String DESCRIPTION = "Add a trigger. When a trigger text is said, an automatic reply happens. (admin only)";

    @Inject
    TriggerService triggerService;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.TRIGGER, null, NAME),
                DESCRIPTION,
                List.of(InteractionContextType.GUILD),
                null,
                SlashCommandCategory.ADMIN,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply("Please answer the questions to create your trigger ^^").setEphemeral(true).queue();
        new AddTriggerQuestionnaire(triggerService).startQuestionnaire(event.getChannel(), event.getUser().getIdLong());
    }
}
