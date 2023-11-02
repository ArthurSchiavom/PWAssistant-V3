package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.info;

import com.github.ArthurSchiavom.pwassistant.boundary.DisplayUtils;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.DefaultEmbedConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.control.config.PWI;
import io.quarkus.arc.All;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class HelpCommand implements SlashCommand {

    @Inject
    @All
    List<SlashCommand> slashCommands;

    private static final String NAME = "help";
    private static final String DESCRIPTION = "See a nice list of my commands";

    private MessageEmbed helpMessage;

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
        event.replyEmbeds(helpMessage).queue();
    }

    void onStart(@Observes StartupEvent ev) {
        helpMessage = buildHelpMessage();
    }

    private MessageEmbed buildHelpMessage() {
        final EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(DefaultEmbedConfig.DEFAULT_EMBED_COLOR);
        eb.setThumbnail(PWI.PWI_ICON_URL);
        for (final SlashCommandCategory value : SlashCommandCategory.values()) {
            final List<SlashCommand> categoryCommands = slashCommands.stream()
                    .filter(c -> c.getSlashCommandInfo().getCategory() == value)
                    .toList();
            eb.addField(createCategoryField(value, categoryCommands));
        }
        return eb.build();
    }

    private MessageEmbed.Field createCategoryField(final SlashCommandCategory category, final List<SlashCommand> commands) {
        return new MessageEmbed.Field(category.getDisplayName(), buildCommandList(commands), false);
    }

    private String buildCommandList(final List<SlashCommand> commands) {
        final List<SlashCommandInfo> commandInfos = commands.stream()
                .map(SlashCommand::getSlashCommandInfo)
                .sorted()
                .toList();
        final Iterator<SlashCommandInfo> commandsIt = commandInfos.iterator();
        final StringBuilder sb = new StringBuilder();
        final SlashCommandInfo firstCommandInfo = commandsIt.next();
        sb.append(" • /**").append(firstCommandInfo.getPath().getFullPath()).append("**").append(" - ").append(firstCommandInfo.getDescription());
        commandsIt.forEachRemaining(c -> sb.append("\n • /**").append(c.getPath().getFullPath()).append("**").append(" - ").append(c.getDescription()));
        return sb.append("\n").append(DisplayUtils.INVISIBLE_CHARACTER).toString();
    }
}
