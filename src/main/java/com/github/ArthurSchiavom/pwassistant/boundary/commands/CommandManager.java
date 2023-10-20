package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class CommandManager {
    @Inject
    Instance<SlashCommand> slashCommandInstances;

    private final Map<String, SlashCommand> slashCommands = new HashMap<>();
    private final List<CommandData> commandDataList = new ArrayList<>();

    void onStart(@Observes StartupEvent ev) {
        slashCommandInstances.stream().forEach(command -> {
            slashCommands.put(command.getCommandData().getName(), command);
            commandDataList.add(command.getCommandData());
        });
    }

    public List<CommandData> getJdaCommands() {
        return new ArrayList<>(commandDataList);
    }

    public SlashCommand getSlashCommand(final String name) {
        return slashCommands.get(name);
    }
}
