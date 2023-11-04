package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import com.github.ArthurSchiavom.pwassistant.entity.PwiClass;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

import java.util.Arrays;
import java.util.List;

public class PwiClassChoices implements Choice<PwiClass> {
    private static final List<Command.Choice> choices = Arrays.stream(PwiClass.values())
            .map(pwiClass -> new Command.Choice(pwiClass.getName(), pwiClass.getName()))
            .toList();

    @Override
    public List<Command.Choice> getChoices() {
        return choices;
    }

    @Override
    public PwiClass getOptionObjectFromPayload(final CommandInteractionPayload payload, final String choiceName) {
        return PwiClass.fromString(ChoiceUtils.getStringOption(payload, choiceName));
    }
}
