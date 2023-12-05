package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.Trigger;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TriggerCachedRepository extends CachedRepository<Trigger> implements InitializableCachedRepo {

    @Inject
    TriggerRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }
}
