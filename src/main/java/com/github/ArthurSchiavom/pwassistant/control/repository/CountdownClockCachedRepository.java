package com.github.ArthurSchiavom.pwassistant.control.repository;

import com.github.ArthurSchiavom.pwassistant.entity.CountdownClock;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class CountdownClockCachedRepository extends CachedRepository<CountdownClock> {

    @Inject
    CountdownClockRepository repo;

    void onStart(@Observes StartupEvent ev) {
        this.init(repo);
        System.out.println("a");
    }
}
