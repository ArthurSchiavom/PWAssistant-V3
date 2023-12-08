package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.schedule;

import com.github.ArthurSchiavom.pwassistant.boundary.JdaProvider;
import com.github.ArthurSchiavom.pwassistant.control.schedule.ScheduledMessageService;
import com.github.ArthurSchiavom.pwassistant.entity.ScheduledMessage;
import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class ScheduleExecutor {

    @Inject
    ScheduledMessageService scheduleService;

    @Inject
    GlobalConfig globalConfig;
    @Inject
    JdaProvider jdaProvider;

    // At every minute from 0 through 59
    @Scheduled(cron = "0 0-59 * * * ?", delay = 60L, delayUnit = TimeUnit.SECONDS)
    void execute() {
        if (globalConfig.isTestBot()) {
            return;
        }

        final LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);
        final List<ScheduledMessage> schedules = scheduleService.getAllScheduledMessages();
        for (final ScheduledMessage schedule : schedules) {
            if (scheduleService.isPastExecutionTime(schedule, currentTime)) {
                jdaProvider.getJda().getTextChannelById(schedule.getChannelId())
                        .sendMessage(schedule.getMessage()).queue();
                scheduleService.updateNextExecutionTime(schedule, currentTime);
                scheduleService.update(schedule);
            }
        }
    }
}
