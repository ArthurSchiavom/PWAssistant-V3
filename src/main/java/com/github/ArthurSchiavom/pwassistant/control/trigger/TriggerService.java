package com.github.ArthurSchiavom.pwassistant.control.trigger;

import com.github.ArthurSchiavom.pwassistant.control.repository.TriggerCachedRepository;
import com.github.ArthurSchiavom.pwassistant.entity.Trigger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TriggerService {
    @Inject
    TriggerCachedRepository triggerRepo;

    public void addTrigger(final Trigger trigger) {
        triggerRepo.create(trigger);
    }

    public List<Trigger> getTriggersOfServer(final long serverId) {
        return triggerRepo.getTriggersForServer(serverId);
    }

    public void deleteTrigger(final Trigger trigger) {
        triggerRepo.delete(trigger);
    }

    public boolean deleteTriggerByServerAndTriggerName(final long serverId, final String triggerName) {
        final List<Trigger> triggers = getTriggerByServerAndTriggerName(serverId, triggerName);
        if (triggers.isEmpty()) {
            return false;
        }

        triggers.forEach(this::deleteTrigger);
        return true;
    }

    private List<Trigger> getTriggerByServerAndTriggerName(final long serverId, final String triggerName) {
        final String triggerNameLc = triggerName.toLowerCase();
        return triggerRepo.getTriggersForServer(serverId).stream()
                .filter(t -> t.getTriggerTextLowercase().toLowerCase().equals(triggerNameLc))
                .toList();
    }
}
