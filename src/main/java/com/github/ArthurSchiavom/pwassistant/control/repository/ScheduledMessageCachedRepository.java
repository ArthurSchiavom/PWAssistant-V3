package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.ScheduledMessage;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ScheduledMessageCachedRepository extends CachedRepository<ScheduledMessage> {

    @Inject
    ScheduledMessageRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }
}
