package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

import java.util.List;

public interface Choice<T> {
    List<Command.Choice> getChoices();
    T getOptionObjectFromPayload(final CommandInteractionPayload payload, final String choiceName);
    default List<Command.Choice> getAutocompleteChoices(final String userInput) {
        if (userInput == null) {
            return getChoices();
        }

        final String userInputLc = userInput.toLowerCase();
        return getChoices().stream().filter(c -> c.getName().toLowerCase().contains(userInputLc)).toList();
    }
}
