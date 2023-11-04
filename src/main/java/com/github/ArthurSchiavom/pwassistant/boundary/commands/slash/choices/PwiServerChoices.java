package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

import java.util.Arrays;
import java.util.List;

public class PwiServerChoices implements Choice<PwiServer> {
    private static final List<Command.Choice> choices = Arrays.stream(PwiServer.values())
            .map(pwiServer -> new Command.Choice(pwiServer.getName(), pwiServer.getName()))
            .toList();

    @Override
    public List<Command.Choice> getChoices() {
        return choices;
    }

    @Override
    public PwiServer getOptionObjectFromPayload(final CommandInteractionPayload payload, final String choiceName) {
        return PwiServer.fromString(ChoiceUtils.getStringOption(payload, choiceName));
    }
}
