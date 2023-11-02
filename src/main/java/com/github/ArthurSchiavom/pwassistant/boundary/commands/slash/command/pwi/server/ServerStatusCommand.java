package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi.server;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.github.ArthurSchiavom.pwassistant.control.config.PWI.PWI_ICON_URL;
import static net.dv8tion.jda.api.utils.messages.MessageCreateData.fromEmbeds;

@ApplicationScoped
public class ServerStatusCommand implements SlashCommand {
    private final String DA_SERVER_URL = "pwieu3.en.perfectworld.eu";
    private final String ET_SERVER_URL = "pwiwest4.playpwi.com";
    private final String TT_SERVER_URL = "pwigc2.playpwi.com";
    private final String TI_SERVER_URL = "pwieast2.playpwi.com";
    private final int SERVER_PORT = 29000;

    private static final String SUBCOMMAND_NAME = "status";
    private static final String DESCRIPTION = "Check if PWI servers are up";

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

            h.editOriginal(MessageEditData.fromEmbeds(eb.build())).queue();
        });
    }

    private String getServersAvailabilityDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("**Dawnglory** ").append(serverAvailabilityCheck(DA_SERVER_URL, SERVER_PORT))
                .append("\n\n**Etherblade** ").append(serverAvailabilityCheck(ET_SERVER_URL, SERVER_PORT))
                .append("\n\n**Twilight Temple** ").append(serverAvailabilityCheck(TT_SERVER_URL, SERVER_PORT))
                .append("\n\n**Tideswell** ").append(serverAvailabilityCheck(TI_SERVER_URL, SERVER_PORT));
//        sb.append("**Dawnglory** ").append(serverAvailabilityCheck(DA_SERVER_URL, SERVER_PORT))
//                .append("\n\n**Etherblade**: unknown")
//                .append("\n\n**Twilight Temple**: unknown")
//                .append("\n\n**Tideswell**: unknown")
//                .append("\n\n(The ip addresses of et, tt and ti have changed. If you know the new ips please share them with papakingu, this bot's dev ♥)");

        return sb.toString();
    }

    /**
     * Checks if a server is reachable or not and returns a string accordingly.
     *
     * @param serverAddress the server's address to use for the connection.
     * @param serverPort    the port to use for the connection.
     * @return "down" or "up" according to whether the program fails to connect or no and "not responding. Probably booting up." if the server does not respond.
     */
    private String serverAvailabilityCheck(final String serverAddress, final int serverPort) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(serverAddress, serverPort), 1000);
            return "✅";
        } catch (Exception e) {
            return "down";
        } finally {
            if (!s.isClosed())
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
