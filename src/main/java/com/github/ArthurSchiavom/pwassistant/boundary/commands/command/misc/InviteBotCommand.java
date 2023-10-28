package com.github.ArthurSchiavom.pwassistant.boundary.commands.command.misc;

import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.SlashCommand;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@ApplicationScoped
public class InviteBotCommand implements SlashCommand {
    private static final String NAME = "invite";
    private static final String DESCRIPTION = "Invite me (" + Bot.NAME + ") to your server!";

    private static final String INVITE_URL = "[Invite](https://discord.com/oauth2/authorize?client_id=377542452493680660&scope=bot&permissions=416745512256)";
    private static final String INVITE_MESSAGE = "You can invite me to your server by clicking on " + INVITE_URL + "!";

    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);
    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(INVITE_MESSAGE).queue();
    }
}
