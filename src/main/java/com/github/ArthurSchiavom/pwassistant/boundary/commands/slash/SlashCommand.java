package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

public interface SlashCommand {
    void execute(final SlashCommandInteractionEvent event);
    SlashCommandInfo getSlashCommandInfo();
    default List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        return null;
    }
}
