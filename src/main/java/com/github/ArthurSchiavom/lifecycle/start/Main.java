package com.github.ArthurSchiavom.lifecycle.start;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class Main {

    void onStart(@Observes StartupEvent ev) { // TODO REMOVE
        log.info("------------------ STARTING");
        Bootstrap.load();
        log.info("------------------ DONE");
    }
}
