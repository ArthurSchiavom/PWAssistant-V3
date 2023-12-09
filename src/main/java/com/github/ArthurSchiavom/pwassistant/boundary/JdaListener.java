package com.github.ArthurSchiavom.pwassistant.boundary;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.CommandManager;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden.HiddenCommandExecutor;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin.trigger.TriggerExecutor;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.QuestionnaireRegister;
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

    @Inject
    TriggerExecutor triggerExecutor;

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
        if (event.getAuthor().isBot()) {
            return;
        }

        if (QuestionnaireRegister.getInstance().processPossibleReply(event)) {
            return;
        }

        hiddenCommandExecutor.process(event);
        triggerExecutor.applyTriggers(event.getGuild().getIdLong(), event.getChannel(), event.getMessage().getContentRaw());
    }
}
