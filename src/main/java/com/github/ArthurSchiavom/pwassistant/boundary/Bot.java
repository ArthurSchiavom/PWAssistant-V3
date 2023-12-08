package com.github.ArthurSchiavom.pwassistant.boundary;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.CommandManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Slf4j
public class Bot {

    public static final String NAME = "PWAssistant";

    @Inject
    JdaProvider jdaProvider;

    @ConfigProperty(name = "discord.bot.token")
    String token;
    @ConfigProperty(name = "discord.bot.registercommands", defaultValue = "true")
    boolean registerCommands;

    @ConfigProperty(name = "discord.bot.activity.type")
    String activityType;
    @ConfigProperty(name = "discord.bot.activity.description")
    String activityDescription;

    @Inject
    CommandManager commandManager;
    @Inject
    JdaListener jdaListener;

    @SneakyThrows
    public void init() {
        if (jdaProvider.getJda() != null) {
            throw new UnsupportedOperationException("Attempted to start JDA twice");
        }

        jdaProvider.setJda(JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.SCHEDULED_EVENTS)
                .setActivity(Activity.of(Activity.ActivityType.valueOf(activityType), activityDescription))
                .addEventListeners(jdaListener)
                .build().awaitReady());
        if (registerCommands) {
            jdaProvider.getJda().updateCommands().addCommands(commandManager.getJdaCommands()).complete();
        }
    }

    public void shutdown() {
		final JDA jda = jdaProvider.getJda();
        try {
            jda.shutdown();
            boolean success = jda.awaitShutdown(30, TimeUnit.SECONDS);
            if (!success) {
                log.error("JDA took too long to shutdown, forcing");
                jda.shutdownNow();
                success = jda.awaitShutdown(30, TimeUnit.SECONDS);
                if (success) {
                    log.error("JDA was forcefully terminated");
                } else {
                    log.error("Could not forcefully terminate JDA");
                }
            }
        } catch (InterruptedException e) {
            log.error("FAILED TO SHUTDOWN JDA", e);
        }
    }
}
