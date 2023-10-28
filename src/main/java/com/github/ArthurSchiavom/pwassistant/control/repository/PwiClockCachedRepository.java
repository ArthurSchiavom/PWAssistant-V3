package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PwiClockCachedRepository extends CachedRepository<PwiClock> {

    @Inject
    PwiClockRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }
}
