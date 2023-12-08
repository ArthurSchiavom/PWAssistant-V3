package com.github.ArthurSchiavom.pwassistant.control.schedule;

import com.github.ArthurSchiavom.pwassistant.control.repository.ScheduledMessageCachedRepository;
import com.github.ArthurSchiavom.pwassistant.entity.ScheduledMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
@Transactional
public class ScheduledMessageService {
    @Inject
    ScheduledMessageCachedRepository scheduledMessageRepo;

    public void addSchedule(final ScheduledMessage newScheduledMessage) {
        newScheduledMessage.setNextExecutionTime(LocalDateTime.now(ZoneOffset.UTC));
        updateNextExecutionTime(newScheduledMessage, LocalDateTime.now(ZoneOffset.UTC));
        scheduledMessageRepo.create(newScheduledMessage);
    }

    public void updateNextExecutionTime(final ScheduledMessage scheduledMessage, final LocalDateTime currentTime) {
        if (isPastExecutionTime(scheduledMessage, currentTime)) {
            // Conversion for compatibility with V2 code, as the schedule computation was not rebuilt from scratch :x
            final Calendar nextExecutionTimeCal = scheduledMessage.getRepetitionType().getNext(scheduledMessage.getScheduleDays(), scheduledMessage.getHour(), scheduledMessage.getMinute());
            final LocalDateTime nextExecutionTime = LocalDateTime.ofInstant(nextExecutionTimeCal.toInstant(), ZoneOffset.UTC);
            scheduledMessage.setNextExecutionTime(nextExecutionTime);
        }
    }

    public boolean isPastExecutionTime(final ScheduledMessage scheduledMessage, final LocalDateTime currentTime) {
        return scheduledMessage.getNextExecutionTime().isAfter(currentTime);
    }

    public List<ScheduledMessage> getAllScheduledMessages() {
        return scheduledMessageRepo.getAllItems();
    }

    public void update(final ScheduledMessage scheduledMessage) {
        scheduledMessageRepo.update(scheduledMessage);
    }

    public List<ScheduledMessage> getAllScheduledMessagesForServer(final long serverId) {
        // We don't have a lot so this is fine. Do add use a hashmap instead if this becomes slow.
        return scheduledMessageRepo.getAllItems().stream().filter(s -> s.getServerId() == serverId).toList();
    }

    public void delete(final long serverId, final String scheduleName) {
        scheduledMessageRepo.getAllItems().stream()
                .filter(s -> s.getServerId() == serverId && s.getScheduleName().equalsIgnoreCase(scheduleName))
                .forEach(s -> scheduledMessageRepo.delete(s));
    }
}
