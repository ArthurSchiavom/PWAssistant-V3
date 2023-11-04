package com.github.ArthurSchiavom.pwassistant.boundary.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChoiceUtils {
    public static String getChoiceAsString(final CommandAutoCompleteInteractionEvent event, final String optionName) {
        final OptionMapping optionMapping = event.getOption(optionName);
        return optionMapping == null ? null : optionMapping.getAsString();
    }
}
