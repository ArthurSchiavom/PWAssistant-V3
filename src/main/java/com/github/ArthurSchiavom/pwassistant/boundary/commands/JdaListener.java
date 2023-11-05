package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommandExecutor;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

@ApplicationScoped
public class JdaListener extends ListenerAdapter {

    @Inject
    CommandManager commandManager;

    @Inject
    HiddenCommandExecutor hiddenCommandExecutor;

    @Override
    public void onSlashCommandInteraction(@Nonnull final SlashCommandInteractionEvent event) {
        commandManager.execute(event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(@Nonnull final CommandAutoCompleteInteractionEvent event) {
        final List<Command.Choice> options = commandManager.getAutoCompletion(event);
        if (options != null && !options.isEmpty()) {
            event.replyChoices(options).queue();
        }
    }

    @Override
    public void onMessageReceived(@Nonnull final MessageReceivedEvent event) {
        hiddenCommandExecutor.process(event);
    }
}
