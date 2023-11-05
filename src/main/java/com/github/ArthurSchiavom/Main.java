package com.github.ArthurSchiavom;

import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.CommandManager;
import com.github.ArthurSchiavom.pwassistant.control.pwi.PwiItemService;
import com.github.ArthurSchiavom.shared.control.repository.CachedRepository;
import io.quarkus.arc.All;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
@Slf4j
public class Main {
    @Inject
    Bot bot;
    @Inject
    CommandManager commandManager;
    @Inject
    @All
    List<CachedRepository> repos;
    @Inject
    PwiItemService pwiItemService;


    void onStart(@Observes StartupEvent ev) throws IOException {
        log.info("------------------ STARTING");

        // Independent (just needs to be done before the bot logs in)
        pwiItemService.init();

        // Order matters
        repos.forEach(CachedRepository::init);
        commandManager.init();
        bot.init();

        log.info("------------------ STARTUP COMPLETE");
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("------------------ SHUTTING DOWN");

        bot.shutdown();

        log.info("------------------ SHUTDOWN COMPLETE");
    }
}
