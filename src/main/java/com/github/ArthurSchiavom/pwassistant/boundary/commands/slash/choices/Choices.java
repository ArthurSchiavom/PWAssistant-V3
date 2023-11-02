package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Choices {
    private static final List<Command.Choice> SERVERS = List.of(
            new Command.Choice(PwiServer.ETHERBLADE.getName(), PwiServer.ETHERBLADE.getName()),
            new Command.Choice(PwiServer.TWILIGHT_TEMPLE.getName(), PwiServer.TWILIGHT_TEMPLE.getName()),
            new Command.Choice(PwiServer.TIDESWELL.getName(), PwiServer.TIDESWELL.getName()),
            new Command.Choice(PwiServer.DAWNGLORY.getName(), PwiServer.DAWNGLORY.getName())
    );

    public static List<Command.Choice> getPwiServerChoices() {
        return SERVERS;
    }
    private static PwiServer getPwiServerFromChoice(final String choice) {
        return PwiServer.fromString(choice);
    }
    public static PwiServer getPwiServerFromChoice(final List<OptionMapping> options, final int paramIndex) {
        return getPwiServerFromChoice(options.get(paramIndex).getAsString());
    }
}
