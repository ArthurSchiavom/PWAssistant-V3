package com.github.ArthurSchiavom.pwassistant.boundary.commands.base2;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

public interface SlashCommand2 {
    /**
     * <br>Ex1:
     * <br>First = command
     * <br>Second = subcommand group
     * <br>Third = subcommand group
     * <br>Fourth = subcommand
     * <br><br>Ex2:
     * <br>First = command
     * <br>Second = subcommand group
     * <br>Third = subcommand
     * <br><br>Ex3:
     * <br>First = command
     * <br>Second = subcommand
     */
    List<SlashCommandInfo> slashCommandInfoTree();
    void execute(final SlashCommandInteractionEvent event);

    default List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        return null;
    }
}
