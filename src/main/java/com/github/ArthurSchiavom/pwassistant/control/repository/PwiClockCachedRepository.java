package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class PwiClockCachedRepository extends CachedRepository<PwiClock> {

    @Inject
    PwiClockRepository repo;

    void onStart(@Observes StartupEvent ev) {
        this.init(repo);
    }
}
