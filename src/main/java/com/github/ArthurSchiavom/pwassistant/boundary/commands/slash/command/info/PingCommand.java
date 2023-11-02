package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@ApplicationScoped
public class PingCommand implements SlashCommand {

    private static final String NAME = "ping";
    private static final String DESCRIPTION = "PWAssistant's ping to this Discord server";
    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                false,
                null,
                SlashCommandCategory.INFO);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong! (checking ping...)")
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong!\n%d ms", System.currentTimeMillis() - time)
                ).queue();
    }
}
