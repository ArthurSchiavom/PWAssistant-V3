package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@Slf4j
public class ClearCommand implements SlashCommand {
    private static final String NAME = "clear";
    private static final String DESCRIPTION = "Bulk delete the last messages of this channel. (command user must have Manage Messages permission)";
    private static final String OPTION_NAME_MSG_COUNT = "number-of-messages";
    private static final String OPTION_DESCRIPTION_MSG_COUNT = "Number of messages to delete";
    private static final String OPTION_NAME_FIRST_MESSAGE = "first-message-id";
    private static final String OPTION_DESCRIPTION_FIRST_MESSAGE = "(OPTIONAL) ID of the first message to delete. The number-of-messages before this will be deleted.";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(NAME, null, null),
                DESCRIPTION,
                true,
                List.of(new OptionData(OptionType.INTEGER, OPTION_NAME_MSG_COUNT, OPTION_DESCRIPTION_MSG_COUNT, true, false),
                        new OptionData(OptionType.STRING, OPTION_NAME_FIRST_MESSAGE, OPTION_DESCRIPTION_FIRST_MESSAGE, false, false)),
                SlashCommandCategory.ADMIN,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_HISTORY, Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the number of messages to delete when using the command").setEphemeral(true).queue();
            return;
        }

        final int numberToDelete = event.getOption(OPTION_NAME_MSG_COUNT).getAsInt() - 1;
        if (numberToDelete < 1) {
            event.reply("You can only delete 2 or more messages.").setEphemeral(true).queue();
            return;
        }

        final OptionMapping firstMessageIdOptionMapping = event.getOption(OPTION_NAME_FIRST_MESSAGE);
        final long firstMessageId = firstMessageIdOptionMapping != null ? firstMessageIdOptionMapping.getAsLong() : event.getChannel().getLatestMessageIdLong();

        event.reply("Deleting. . .").setEphemeral(true).queue(h1 -> {
            final MessageChannel channel = event.getChannel();

            channel.getHistoryBefore(firstMessageId, numberToDelete).queue(
                    (history) -> {
                        channel.retrieveMessageById(firstMessageId).queue(firstMessage -> {
                            final List<Message> msgs = new ArrayList<>(history.getRetrievedHistory());
                            msgs.add(firstMessage);

                            if (msgs.size() == 1) {
                                msgs.get(0).delete().queue();
                            } else {
                                final CompletableFuture<Void>[] deleteFutures = channel.purgeMessages(msgs).toArray(new CompletableFuture[0]);
                                CompletableFuture.allOf(deleteFutures)
                                        .whenCompleteAsync((v, t) -> h1.editOriginal("Deleted")
                                                .queue(ignored -> {}, throwable -> {/* ephemeral message was probably closed */}));
                            }
                        }, t -> {
                            if (t instanceof InsufficientPermissionException) {
                                h1.sendMessage("I don't have permissions to delete messages.").setEphemeral(true).queue();
                            } else {
                                h1.sendMessage("I wasn't able to delete the messages requested.").setEphemeral(true).queue();
                            }
                        });
                    }
                    , t -> {
                        if (t instanceof InsufficientPermissionException) {
                            h1.sendMessage("I don't have permissions to see the message history.").setEphemeral(true).queue();
                        } else {
                            h1.sendMessage("I wasn't able to delete the messages requested.").setEphemeral(true).queue();
                        }
                    });

        });
    }
}
