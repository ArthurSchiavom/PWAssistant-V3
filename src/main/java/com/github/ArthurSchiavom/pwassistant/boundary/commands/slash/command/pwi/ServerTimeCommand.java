package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi;

import com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.boundary.utils.DisplayUtils;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;

import java.util.List;

@ApplicationScoped
public class ServerTimeCommand implements SlashCommand {
    private static final String SUBCOMMAND_NAME = "time";
    private static final String DESCRIPTION = "See the current time in PWI servers";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.SERVER, SUBCOMMAND_NAME),
                DESCRIPTION,
                List.of(InteractionContextType.GUILD),
                null,
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        final StringBuilder sb = new StringBuilder();
        for (final PwiServer server : PwiServer.values()) {
            sb.append("\n\n 🕒 **" ).append(server.getName()).append("**: ").append(DisplayUtils.formatCalendar(server.getCurrentTime()));
        }

        final EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Current Time");
        eb.setThumbnail(BoundaryConfig.PWI_ICON_URL);
        eb.setColor(BoundaryConfig.DEFAULT_EMBED_COLOR);
        eb.setDescription(sb.toString());

        event.replyEmbeds(eb.build()).queue();
    }
}
