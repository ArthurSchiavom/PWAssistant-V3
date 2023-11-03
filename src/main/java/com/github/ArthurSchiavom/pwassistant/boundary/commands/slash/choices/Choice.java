package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

import java.util.List;

public interface Choice<T> {
    List<Command.Choice> getChoices();
    T getOptionObjectFromPayload(final CommandInteractionPayload payload, final String choiceName);
}
