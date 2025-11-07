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

import java.util.List;

@ApplicationScoped
public class TriggerConsultCommand implements SlashCommand {
    private static final String NAME = "consult";
    private static final String DESCRIPTION = "Consult the triggers configured. (admin only)";

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
        final StringBuilder sb = new StringBuilder("Triggers: ");
        triggerService.getTriggersOfServer(event.getGuild().getIdLong())
                .forEach(s -> sb.append("\n* **").append(s.getTriggerTextLowercase()).append("**"));
        event.reply(sb.toString()).queue();
    }
}
