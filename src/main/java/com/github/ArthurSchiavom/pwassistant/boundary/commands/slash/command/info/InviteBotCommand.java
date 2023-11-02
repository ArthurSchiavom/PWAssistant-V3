package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@ApplicationScoped
public class InviteBotCommand implements SlashCommand {
    private static final String NAME = "invite";
    private static final String DESCRIPTION = "Invite me (" + Bot.NAME + ") to your server!";

    private static final String INVITE_URL = "[Invite](https://discord.com/oauth2/authorize?client_id=377542452493680660&scope=bot&permissions=416745512256)";
    private static final String INVITE_MESSAGE = "You can invite me to your server by clicking on " + INVITE_URL + "!";

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
        event.reply(INVITE_MESSAGE).queue();
    }
}
