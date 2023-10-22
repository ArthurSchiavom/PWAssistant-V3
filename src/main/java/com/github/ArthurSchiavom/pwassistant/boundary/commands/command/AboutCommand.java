package com.github.ArthurSchiavom.pwassistant.boundary.commands.command;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.SlashCommand;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@ApplicationScoped
public class AboutCommand implements SlashCommand {
    private static final String NAME = "about";
    private static final String DESCRIPTION = "Are you curious about me?";

    private static final String MESSAGE = """
            Hello!

            I was initially released in November 2017 with the purpose of helping administrate and serve the members of **PWI Kingdom**.
            PWI Kingdom is a Discord server for PWI players to discuss PWI stuff, just hang around, meet people, get help, stay informed, etc.
            I also provide this server with more commands and features!

            We currently have over 300 members from all servers online every day!
            **__If you wish to join, we'll be glad to welcome you!__**

            ♥ [CLICK HERE TO JOIN PWI KINGDOM!](https://discord.gg/pwi-kingdom-251460250115375114)
            If you're interested in my source code, it's all [here](https://github.com/ArthurSchiavom/PWAssistant-V3).""";

    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);
    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(MESSAGE).queue();
    }
}
