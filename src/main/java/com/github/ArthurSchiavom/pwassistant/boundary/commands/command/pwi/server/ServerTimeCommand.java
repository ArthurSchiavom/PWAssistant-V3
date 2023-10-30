package com.github.ArthurSchiavom.pwassistant.boundary.commands.command.pwi.server;

import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

@ApplicationScoped
public class ServerTimeCommand implements SlashCommand {
    private static final String NAME = "pwiservertime";
    private static final String DESCRIPTION = "See the current time in PWI servers";
    private final CommandData commandData = Commands.slash("pwi", "1")
            .addSubcommandGroups(new SubcommandGroupData("server", "2")
                    .addSubcommands(new SubcommandData("time", "3")))
            .addSubcommands(new SubcommandData("status", "4"));

    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply("ok").setEphemeral(true).queue();
        System.out.println("full " + event.getFullCommandName());
        System.out.println("cmd " + event.getCommandString());
        System.out.println("sub " + event.getSubcommandName());
        System.out.println("group " + event.getSubcommandGroup());
    }
}
