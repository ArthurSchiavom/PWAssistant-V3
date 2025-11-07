package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi;

import com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.control.externalservice.HttpTextReader;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.utils.SplitUtil;

import java.util.List;

import static net.dv8tion.jda.api.entities.Message.MAX_CONTENT_LENGTH;

@ApplicationScoped
public class CodesCommand implements SlashCommand {
    private static final String NAME = "codes";
    private static final String DESCRIPTION = "Get a list of active PWI codes!";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, null, NAME),
                DESCRIPTION,
                List.of(InteractionContextType.GUILD),
                null,
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        String messageFull = HttpTextReader.readText(BoundaryConfig.PASTEBIN_URL);
        List<String> messagesSplit = SplitUtil.split(messageFull, MAX_CONTENT_LENGTH, true, SplitUtil.Strategy.onChar('#'));
        event.reply(messagesSplit.getFirst()).queue();
        for (int i = 1; i < messagesSplit.size(); i++) {
            final String nextMessage = messagesSplit.get(i);
            event.getChannel().sendMessage(nextMessage).queue();
        }
    }
}
