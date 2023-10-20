package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@ApplicationScoped
public class JdaListener extends ListenerAdapter {

    @Inject
    CommandManager commandManager;

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        final SlashCommand slashCommand = commandManager.getSlashCommand(event.getName());
        if (slashCommand != null) {
            slashCommand.execute(event);
        }
    }
}
