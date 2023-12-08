package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.schedule;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.control.schedule.ScheduledMessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

@ApplicationScoped
public class ScheduleConsultCommand implements SlashCommand {
    private static final String NAME = "consult";
//    private static final String DESCRIPTION = "Add a trigger. When a trigger text is said, an automatic reply happens. (admin only)";
    private static final String DESCRIPTION = "[BETA] Consult the schedules configured. (admin only)";

    @Inject
    ScheduledMessageService scheduleService;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.SCHEDULE, null, NAME),
                DESCRIPTION,
                true,
                null,
                SlashCommandCategory.ADMIN,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        final StringBuilder sb = new StringBuilder("Scheduled messages: ");
        scheduleService.getAllScheduledMessagesForServer(event.getGuild().getIdLong())
                .forEach(s -> sb.append("\n* **").append(s.getScheduleName()).append("**"));
        event.reply(sb.toString()).queue();
    }
}
