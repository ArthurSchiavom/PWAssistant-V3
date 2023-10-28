package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.CountdownClock;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CountdownClockCachedRepository extends CachedRepository<CountdownClock> {

    @Inject
    CountdownClockRepository repo;

    @Override
    public void init() {
        this.init(repo);
    }
}
