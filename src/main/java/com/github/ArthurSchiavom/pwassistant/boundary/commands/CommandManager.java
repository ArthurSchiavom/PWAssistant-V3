package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.JdaProvider;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommandManager {
    @Inject
    @All
    List<SlashCommand> slashCommandList;

    @Inject
    JdaProvider jdaProvider;

    private final Map<SlashCommandPath, SlashCommand> slashCommandMap = new HashMap<>();
    private final List<CommandData> commandDataList = new ArrayList<>();

    public void init() {
        slashCommandMap.putAll(createSlashCommandMap(slashCommandList));
        commandDataList.addAll(createCommandDataList(slashCommandList));
    }

    private List<SlashCommandData> createCommandDataList(final List<SlashCommand> commands) {
        final List<SlashCommandData> newCommandDataList = new ArrayList<>();
        for (final SlashCommand command : commands) {
            final SlashCommandInfo info = command.getSlashCommandInfo();
            final SlashCommandPath path = info.getPath();

            // /command
            SlashCommandData commandData = findCommandData(newCommandDataList, path.getName());
            if (commandData == null) {
                commandData = createCommandData(info);
                newCommandDataList.add(commandData);
            } else if (permissionsDontMatch(commandData, command)) {
                throw new IllegalStateException("Commands with different permissions grouped under the same parent command are not allowed. " +
                        "Detected during the registration of command " + command.getSlashCommandInfo().getPath().getFullPath());
            }

            SubcommandData subcommandData = createSubcommandData(info);

            // /command subgroup subcommand
            if (path.getSubGroupName() != null) {
                SubcommandGroupData subcommandGroup = findSubcommandGroup(commandData, path.getSubGroupName());
                if (subcommandGroup == null) {
                    subcommandGroup = new SubcommandGroupData(path.getSubGroupName(), path.getSubGroupName());
                    commandData.addSubcommandGroups(subcommandGroup);
                }
                subcommandGroup.addSubcommands(subcommandData);
            }
            // /command subcommand
            else if (path.getSubCommandName() != null) {
                commandData.addSubcommands(subcommandData);
            }
        }
        return newCommandDataList;
    }

    private boolean permissionsDontMatch(final SlashCommandData commandData, final SlashCommand command) {
        final SlashCommandInfo info = command.getSlashCommandInfo();
        return !info.getInteractionContextType().containsAll(commandData.getContexts())
                || !commandData.getContexts().containsAll(info.getInteractionContextType())
                || !Objects.equals(info.getDefaultMemberPermissions().getPermissionsRaw(), commandData.getDefaultPermissions().getPermissionsRaw());
    }

    private SlashCommandData createCommandData(SlashCommandInfo info) {
        final SlashCommandData commandData = Commands.slash(info.getPath().getName(), info.getDescription());
        commandData.setContexts(info.getInteractionContextType());
        commandData.setDefaultPermissions(info.getDefaultMemberPermissions());

        if (info.isNotSubcommand()) {
            if (info.hasOptions()) {
                commandData.addOptions(info.getOptions());
            }
        }
        return commandData;
    }

    private SubcommandData createSubcommandData(SlashCommandInfo info) {
        SubcommandData subcommandData = null;
        if (info.isSubcommand()) {
            subcommandData = new SubcommandData(info.getPath().getSubCommandName(), info.getDescription());
            if (info.hasOptions()) {
                subcommandData.addOptions(info.getOptions());
            }
        }
        return subcommandData;
    }

    private SubcommandGroupData findSubcommandGroup(final SlashCommandData commandData, final String groupName) {
        return commandData.getSubcommandGroups().stream()
                .filter(group -> group.getName().equals(groupName))
                .findFirst()
                .orElse(null);
    }

    private SlashCommandData findCommandData(final List<SlashCommandData> commandDataList, final String name) {
        for (final SlashCommandData commandData : commandDataList) {
            if (commandData.getName().equals(name)) {
                return commandData;
            }
        }
        return null;
    }

    private Map<SlashCommandPath, SlashCommand> createSlashCommandMap(final List<SlashCommand> slashCommandList) {
        return slashCommandList.stream()
                .collect(Collectors.toMap(s -> s.getSlashCommandInfo().getPath(), s -> s));
    }

    public List<CommandData> getJdaCommands() {
        return new ArrayList<>(commandDataList);
    }

    public void execute(SlashCommandInteractionEvent event) {
        final SlashCommand command = getSlashCommand(event.getName(), event.getSubcommandGroup(), event.getSubcommandName());
        if (command != null) {
            command.execute(event);

            final String serverName;
            final String serverId;
            if (event.getGuild() != null) {
                serverName = event.getGuild().getName();
                serverId = event.getGuild().getId();
            }
            else {
                serverName = "DM";
                serverId = event.getUser().getId();
            }
            jdaProvider.getJda().getTextChannelById(BoundaryConfig.COMMAND_LOG_CHANNEL_ID)
                    .sendMessage(String.format("Command used at **%s**(%s): `%s`",
                            serverName,
                            serverId,
                            event.getCommandString())
                    ).queue();
        }
    }

    public List<Command.Choice> getAutoCompletion(final CommandAutoCompleteInteractionEvent event) {
        final SlashCommand command = getSlashCommand(event.getName(), event.getSubcommandGroup(), event.getSubcommandName());
        if (command == null) {
            return null;
        }
        return command.getAutoCompletion(event);
    }

    private SlashCommand getSlashCommand(String name, String group, String subcommand) {
        return slashCommandMap.get(new SlashCommandPath(name, group, subcommand));
    }

}
