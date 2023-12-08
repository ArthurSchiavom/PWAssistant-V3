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
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class ScheduleRemoveCommand implements SlashCommand {
    private static final String NAME = "remove";
//    private static final String DESCRIPTION = "Add a trigger. When a trigger text is said, an automatic reply happens. (admin only)";
    private static final String DESCRIPTION = "[BETA] Remove a scheduled message. (admin only)";
    private static final String OPTION_NAME_SCHEDULE_NAME = "schedule-name";
    private static final String OPTION_DESCRIPTION_SCHEDULE_NAME = "name of the schedule";

    @Inject
    ScheduledMessageService scheduleService;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.SCHEDULE, null, NAME),
                DESCRIPTION,
                true,
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_SCHEDULE_NAME, OPTION_DESCRIPTION_SCHEDULE_NAME, true, false)),
                SlashCommandCategory.ADMIN,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the name of the schedule to remove").setEphemeral(true).queue();
            return;
        }

        final String scheduleName = event.getOption(OPTION_NAME_SCHEDULE_NAME).getAsString();
        scheduleService.delete(event.getGuild().getIdLong(), scheduleName);

        event.reply("Schedule " + scheduleName + " removed").queue();
    }
}
