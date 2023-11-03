package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;

import java.util.List;

public class PwiServerChoices implements Choice<PwiServer> {
    private static final List<Command.Choice> SERVERS = List.of(
            new Command.Choice(PwiServer.ETHERBLADE.getName(), PwiServer.ETHERBLADE.getName()),
            new Command.Choice(PwiServer.TWILIGHT_TEMPLE.getName(), PwiServer.TWILIGHT_TEMPLE.getName()),
            new Command.Choice(PwiServer.TIDESWELL.getName(), PwiServer.TIDESWELL.getName()),
            new Command.Choice(PwiServer.DAWNGLORY.getName(), PwiServer.DAWNGLORY.getName())
    );

    @Override
    public List<Command.Choice> getChoices() {
        return SERVERS;
    }

    @Override
    public PwiServer getOptionObjectFromPayload(final CommandInteractionPayload payload, final String choiceName) {
        return PwiServer.fromString(ChoiceUtils.getStringOption(payload, choiceName));
    }
}
