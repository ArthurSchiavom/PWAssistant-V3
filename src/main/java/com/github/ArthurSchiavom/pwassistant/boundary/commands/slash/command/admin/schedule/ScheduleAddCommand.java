package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.schedule;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.messagingSchedule.AddMessagingScheduleQuestionnaire;
import com.github.ArthurSchiavom.pwassistant.control.schedule.ScheduledMessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

@ApplicationScoped
public class ScheduleAddCommand implements SlashCommand {
    private static final String NAME = "add";
    private static final String DESCRIPTION = "Set a message to be repeated every day/day of the week/month. (admin only)";

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
        event.reply("Please answer the questions to create your scheduled message ^^").setEphemeral(true).queue();
        new AddMessagingScheduleQuestionnaire(scheduleService).startQuestionnaire(event.getChannel(), event.getUser().getIdLong());
    }
}
