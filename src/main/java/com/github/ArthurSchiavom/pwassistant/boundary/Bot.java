package com.github.ArthurSchiavom.pwassistant.boundary;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.CommandManager;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.JdaListener;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
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
	@Getter
	private JDA jda;

	@Getter
	@ConfigProperty(name = "testmode")
	boolean isTestBot;

	@ConfigProperty(name = "discord.bot.token")
	String token;

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
		if (jda != null) {
			throw new UnsupportedOperationException("Attempted to start JDA twice");
		}

		jda = JDABuilder.createDefault(token,
						GatewayIntent.DIRECT_MESSAGES,
						GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
						GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_MESSAGE_REACTIONS)
				.disableCache(CacheFlag.VOICE_STATE, CacheFlag.SCHEDULED_EVENTS)
				.setActivity(Activity.of(Activity.ActivityType.valueOf(activityType), activityDescription))
				.addEventListeners(jdaListener)
				.build().awaitReady();
		jda.updateCommands().addCommands(commandManager.getJdaCommands()).queue();
	}

	public void shutdown() {
		try {
			jda.shutdown();
			boolean success = jda.awaitShutdown(30, TimeUnit.SECONDS);
			if (!success) {
				log.error("JDA took too long to shutdown, forcing");
				jda.shutdownNow();
				success = jda.awaitShutdown(30, TimeUnit.SECONDS);
				if (success) {
					log.error("JDA was forcefully terminated");
				}
				else {
					log.error("Could not forcefully terminate JDA");
				}
			}
		} catch (InterruptedException e) {
			log.error("FAILED TO SHUTDOWN JDA", e);
		}
	}
}
