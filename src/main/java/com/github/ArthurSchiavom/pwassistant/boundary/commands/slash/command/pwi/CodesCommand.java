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

@ApplicationScoped
public class CodesCommand implements SlashCommand {
    private static final String NAME = "codes";
    private static final String DESCRIPTION = "Get a list of active PWI codes!";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, null, NAME),
                DESCRIPTION,
                true,
                null,
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(HttpTextReader.readText(BoundaryConfig.PASTEBIN_URL)).queue();
    }
}
