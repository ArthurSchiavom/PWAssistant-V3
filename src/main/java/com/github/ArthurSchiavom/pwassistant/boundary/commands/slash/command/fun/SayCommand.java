package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.fun;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class SayCommand implements SlashCommand {
    private static final String NAME = "say";
    private static final String DESCRIPTION = "Ask me to say something!";
    private static final String OPTION_NAME_MESSAGE = "message";
    private static final String OPTION_NAME_DESCRIPTION = "What you want me to say";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                InteractionContextType.ALL,
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_MESSAGE, OPTION_NAME_DESCRIPTION, true, false)),
                SlashCommandCategory.FUN);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("Please tell me what you want me to say when you use the command.").queue();
            return;
        }

        final String message = event.getOptions().get(0).getAsString();
        event.reply("said!").setEphemeral(true).queue();
        event.getChannel().sendMessage(message).queue();
    }
}
