package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.List;

public interface SlashCommand {
    CommandData getCommandData();
    void execute(final SlashCommandInteractionEvent event);

    default List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        return null;
    }
}
