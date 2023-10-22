package com.github.ArthurSchiavom.pwassistant.boundary.commands.command;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.SlashCommand;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@ApplicationScoped
public class CodesCommand implements SlashCommand {
    private static final String NAME = "codes";
    private static final String DESCRIPTION = "Get a list of active PWI codes!";

    private final static String PASTEBIN_URL = "https://pastebin.com/raw/r4yp66Zt";

    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);

    @Override
    public CommandData getCommandData() {
        return commandData;
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
