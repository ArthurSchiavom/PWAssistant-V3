package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

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

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                false,
                null,
                SlashCommandCategory.INFO);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(MESSAGE).queue();
    }
}
