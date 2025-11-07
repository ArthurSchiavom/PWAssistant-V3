package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.Bot;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;

import static com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig.BOT_INVITE_URL;

@ApplicationScoped
public class InviteBotCommand implements SlashCommand {
    private static final String NAME = "invite";
    private static final String DESCRIPTION = "Invite me (" + Bot.NAME + ") to your server!";

    private static final String INVITE_MESSAGE = "You can invite me to your server by clicking on [Invite](" + BOT_INVITE_URL + ")!";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                InteractionContextType.ALL,
                null,
                SlashCommandCategory.INFO);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(INVITE_MESSAGE).queue();
    }
}
