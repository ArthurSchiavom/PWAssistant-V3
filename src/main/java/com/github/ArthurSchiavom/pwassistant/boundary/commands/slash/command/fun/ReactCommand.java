package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.fun;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class ReactCommand  implements SlashCommand {
    private static final String NAME = "react";
    private static final String DESCRIPTION = "React to a message with any emote from any server I'm in!";
    private static final String OPTION_NAME_MESSAGE_ID = "message-id";
    private static final String OPTION_DESCRIPTION_MESSAGE_ID = "(optional) ID of the message to react to. Don't specify to react to previous message.";
    private static final String OPTION_NAME_EMOTE = "emote-name";
    private static final String OPTION_DESCRIPTION_EMOJI = "Name of the emote to react with";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                false,
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_EMOTE, OPTION_DESCRIPTION_EMOJI, true, false),
                        new OptionData(OptionType.STRING, OPTION_NAME_MESSAGE_ID, OPTION_DESCRIPTION_MESSAGE_ID, false, false)),
                SlashCommandCategory.FUN);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("You need to specify the emote you want me to react with.").setEphemeral(true).queue();
            return;
        }

        final List<OptionMapping> options = event.getOptions();
        final String emojiName = options.get(0).getAsString();
        RichCustomEmoji emote = null;
        String messageId = null;

        if (options.size() > 1) {
            messageId = options.get(1).getAsString();
        }

        List<RichCustomEmoji> emotes = event.getGuild().getEmojisByName(emojiName, true);
        if (!emotes.isEmpty()) {
            emote = emotes.get(0);
        } else {
            emotes = event.getJDA().getEmojisByName(emojiName, true);
            if (!emotes.isEmpty()) {
                emote = emotes.get(0);
            }
        }

        if (messageId == null) {
            messageId = event.getChannel().getLatestMessageId();
        }

        if (emote != null) {
            try {
                event.getChannel().retrieveMessageById(messageId).complete().addReaction(emote).complete();
            } catch (Throwable e) {
                event.getChannel().sendMessage("Looks like I don't have permission to react.").queue();
            }
        }
        else {
            try {
                event.getChannel().retrieveMessageById(messageId).complete().addReaction(Emoji.fromFormatted(emojiName)).complete();
            } catch (Throwable e) {
                event.getChannel().sendMessage("Either that emoji isn't valid or I don't have permission to react.").queue();
            }
        }

        event.reply("reacted!").setEphemeral(true).queue();
    }
}
