package com.github.ArthurSchiavom.pwassistant.boundary.commands.hidden;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface HiddenCommand {
    /**
     * Get the name of the command.
     *
     * @return command name
     */
    String getName();

    /**
     * Process a message received event.
     *
     * @param event event to process
     * @return true if the command was executed, false otherwise
     */
    boolean process(MessageReceivedEvent event);
}
