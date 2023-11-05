package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.control.pwi.PwiServerService;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import static com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig.PWI_ICON_URL;
import static net.dv8tion.jda.api.utils.messages.MessageCreateData.fromEmbeds;

@ApplicationScoped
public class ServerStatusCommand implements SlashCommand {

    private static final String SUBCOMMAND_NAME = "status";
    private static final String DESCRIPTION = "Check if PWI servers are up";
    private final PwiServerService pwiServerService = new PwiServerService();

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.SERVER, SUBCOMMAND_NAME),
                DESCRIPTION,
                true,
                null,
                SlashCommandCategory.PWI);
    }


    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Servers statuses")
                .setDescription("Checking servers. . .");
        Embeds.configDefaultEmbedColor(eb);

        event.reply(fromEmbeds(eb.build())).queue(h -> {
            String statusesString = getServersAvailabilityDisplay();
            eb.setDescription(statusesString);
            eb.setThumbnail(PWI_ICON_URL);
            eb.setColor(BoundaryConfig.DEFAULT_EMBED_COLOR);

            h.editOriginal(MessageEditData.fromEmbeds(eb.build())).queue();
        });
    }

    private String getServersAvailabilityDisplay() {
        final StringBuilder sb = new StringBuilder();
        for (final PwiServer server : PwiServer.values()) {
            sb.append("\n\n**").append(server.getName()).append("** ").append(getServerStatusEmoji(pwiServerService.isServerUp(server)));
        }
        return sb.toString();
    }

    private String getServerStatusEmoji(final boolean serverUp) {
        return serverUp ? "✅" : "offline";
    }
}
