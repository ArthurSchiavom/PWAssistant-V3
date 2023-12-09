package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.Trigger;
import com.github.ArthurSchiavom.shared.control.repository.KeyedCachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class TriggerCachedRepository extends KeyedCachedRepository<Long, Trigger> implements InitializableCachedRepo {

    @Inject
    TriggerRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }

    @Override
    public Long getKey(final Trigger entity) {
        return entity.getServerId();
    }

    public List<Trigger> getTriggersForServer(final long serverId) {
        return this.getItemsOfKey(serverId);
    }
}
