package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig.GITHUB_URL;
import static com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig.MAIN_SERVER_INVITE_URL;

@ApplicationScoped
public class AboutCommand implements SlashCommand {
    private static final String NAME = "about";
    private static final String DESCRIPTION = "Are you curious about me?";
    private final String MESSAGE;

    private static final String MESSAGE_TEMPLATE = """
            Hello!

            I was initially released in November 2017 with the purpose of helping administrate and serve the members of **PWI Kingdom**.
            PWI Kingdom is a Discord server for PWI players to discuss PWI stuff, just hang around, meet people, get help, stay informed, etc.
            I also provide this server with more commands and features!

            We currently have over 300 members from all servers online every day!
            **__If you wish to join, we'll be glad to welcome you!__**

            ♥ [CLICK HERE TO JOIN PWI KINGDOM!](%s)
            If you're interested in my source code, it's all [here](%s).""";

    public AboutCommand() {
        MESSAGE = String.format(MESSAGE_TEMPLATE, MAIN_SERVER_INVITE_URL, GITHUB_URL);
    }

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
