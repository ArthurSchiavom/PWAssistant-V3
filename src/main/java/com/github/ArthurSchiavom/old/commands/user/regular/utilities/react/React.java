package com.github.ArthurSchiavom.old.commands.user.regular.utilities.react;

import com.github.ArthurSchiavom.old.commands.base.Category;
import com.github.ArthurSchiavom.old.commands.base.CommandWithoutSubCommands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class React extends CommandWithoutSubCommands {

    public React() {
        super(Category.UTILITY
                , "React to a message with any emoji of the servers I'm in, animated or otherwise."
                , "[Emoji/emote to react with] [ID of the message to react to, or nothing to react to the message above]"
                , true);
        this.addName("React");
        this.addExample("catWithHeart", "Reacts to the message above with the emoji called catWithHeart.");
        this.addExample("catWithHeart 149663679699353602", "Reacts to the message of ID 149663679699353602 with the emoji called catWithHeart.");
        this.buildHelpMessage();
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        String args = this.extractArgumentsOnly(event.getMessage().getContentDisplay());
        if (args == null) {
            event.getChannel().sendMessage("What's the emoji to react with?").queue();
            return;
        }

        String[] argsSplit = args.split(" ");
        String emojiName = argsSplit[0];
        RichCustomEmoji emote = null;
        String messageId = null;

        if (argsSplit.length > 1) {
            messageId = argsSplit[1];
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
            List<Message> messages = event.getChannel().getHistoryBefore(event.getMessage().getId(), 1).complete().getRetrievedHistory();
            if (!messages.isEmpty()) {
                messageId = messages.get(0).getId();
            }
        }

        if (messageId == null) {
            event.getChannel().sendMessage("No such message found.").queue();
            return;
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

        try {
            event.getMessage().delete().complete();
        } catch (Throwable e) {
        }
    }
}
