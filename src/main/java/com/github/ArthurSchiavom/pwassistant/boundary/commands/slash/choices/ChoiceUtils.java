package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

final class ChoiceUtils {
    public static String getStringOption(final CommandInteractionPayload payload, final String choiceName) {
        final OptionMapping nullableOption = payload.getOption(choiceName);
        return nullableOption == null ? null : nullableOption.getAsString();
    }
}
