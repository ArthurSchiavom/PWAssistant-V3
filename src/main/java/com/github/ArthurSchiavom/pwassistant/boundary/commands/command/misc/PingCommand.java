package com.github.ArthurSchiavom.pwassistant.boundary.commands.command.misc;

import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@ApplicationScoped
public class PingCommand implements SlashCommand {

    private static final String NAME = "ping";
    private static final String DESCRIPTION = "PWAssistant's ping to the Discord server";
    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);

    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong! . . .") // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong!\n%d ms", System.currentTimeMillis() - time) // then edit original
                ).queue();
    }
}
