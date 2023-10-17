package com.github.ArthurSchiavom;

import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class Main {
    @Inject
    Bot bot;

    void onStart(@Observes StartupEvent ev) {
        log.info("------------------ STARTING");

        bot.init();

        log.info("------------------ STARTUP COMPLETE");
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("------------------ SHUTTING DOWN");

        bot.shutdown();

        log.info("------------------ SHUTDOWN COMPLETE");
    }
}
