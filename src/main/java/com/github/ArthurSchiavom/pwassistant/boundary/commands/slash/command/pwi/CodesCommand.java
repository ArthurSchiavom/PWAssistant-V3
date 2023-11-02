package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@ApplicationScoped
public class CodesCommand implements SlashCommand {
    private static final String NAME = "codes";
    private static final String DESCRIPTION = "Get a list of active PWI codes!";

    private final static String PASTEBIN_URL = "https://pastebin.com/raw/r4yp66Zt";

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
        final StringBuilder codes = new StringBuilder();
        try {
            final URL url = new URL(PASTEBIN_URL);
            final Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8);

            codes.append(scanner.nextLine());
            while (scanner.hasNext())
                codes.append("\n").append(scanner.nextLine());

            scanner.close();
        } catch (Exception e) {
            event.reply("Failed to retrieve the list of codes.").queue();
        }

        event.reply(codes.toString()).queue();
    }
}
