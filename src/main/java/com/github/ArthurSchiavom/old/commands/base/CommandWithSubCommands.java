package com.github.ArthurSchiavom.old.commands.base;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A command with sub-com.github.ArthurSchiavom.old.commands
 */
public abstract class CommandWithSubCommands extends Command {
    private List<Command> subCommands = new ArrayList<>();

    /**
     * Creates a new command with sub-com.github.ArthurSchiavom.old.commands.
     *  @param category The command's category.
     * @param description The command's description.
     */
    public CommandWithSubCommands(Category category, String description) {
        super(category, description, false);
    }

    /**
     * Creates a new command with sub-com.github.ArthurSchiavom.old.commands.
     *  @param category The command's category.
     * @param description The command's description.
     * @param superCommand The command's super-command.
     */
    public CommandWithSubCommands(Category category, String description, Command superCommand) {
        super(category, description, false, superCommand);
    }

    /**
     * Adds a sub-command to this command.
     *
     * @param subCommand The sub-command to register.
     */
    protected void addSubCommand(Command subCommand) {
        subCommands.add(subCommand);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void buildHelpMessage() {
        /*
        EMBEDED

        *Command name
        Description

        • Subcommand - Description (unsure about the description)
        • Subcommand2 - Description2 (unsure about the description)

        *Aliases
        Alias1, Alias2

        Footer: Random fact about the bot or something else
         */
        if (this.getNames().size() == 0)
            throw new NullPointerException("No name assigned to this command");

        MessageCreateBuilder messageBuilder = new MessageCreateBuilder();
        EmbedBuilder eb = new EmbedBuilder();

        setHelpEmbedHeader(eb);
        this.setHelpEmbedAliases(eb, this.getNames());
        this.setHelpEmbedColor(eb);

        messageBuilder.setEmbeds(eb.build());
        this.setHelpMessage(messageBuilder.build());
    }

    /**
     * Appends the help embed header to an embed.
     *
     * @param eb The target embed.
     */
    private void setHelpEmbedHeader(EmbedBuilder eb) {
        StringBuilder descriptionAndSubCommandsSB = new StringBuilder();
        descriptionAndSubCommandsSB.append("**").append(this.getDescription()).append("**");
        descriptionAndSubCommandsSB.append("\n");
        for (Command subCommand : subCommands) {
            descriptionAndSubCommandsSB.append("\n• ").append(subCommand.getNames().get(0));
        }

        Iterator<String> namesIt = this.getNames().iterator();
        String firstName = namesIt.next();
        eb.addField(firstName + " [Option]", descriptionAndSubCommandsSB.toString(), false);
    }

    @Override
    public Command isThisCommand(String messageLowerCaseNoPrefix) {
        for (Command command : subCommands) {
            if (command.isThisCommand(messageLowerCaseNoPrefix) != null) {
                return command;
            }
        }
        return super.isThisCommand(messageLowerCaseNoPrefix);
    }

    @Override
    protected void runCommandActions(MessageReceivedEvent event) {
        event.getChannel().sendMessage(this.getHelpMessage()).queue();
    }
}
