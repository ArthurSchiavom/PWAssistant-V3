package com.github.ArthurSchiavom.old.commands.base;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A command without sub-com.github.ArthurSchiavom.old.commands
 */
public abstract class CommandWithoutSubCommands extends Command {
    private String arguments;
    private List<String> examples = new ArrayList<>();

    /**
     * Creates a new command without subcommands.
     *
     * @param category The command's category.
     * @param description The command's description.
     * @param arguments This com.github.ArthurSchiavom.old.commands arguments as "[param1] [param2] ...". Can be null.
     * @param runInNewThread If the command actions should run on a new thread.
     * @param superCommand This command's super command.
     */
    public CommandWithoutSubCommands(Category category, String description, String arguments, boolean runInNewThread, Command superCommand) {
        super(category, description, runInNewThread, superCommand);
        initialize(arguments);
    }

    /**
     * Creates a new command without subcommands.
     *
     * @param category The command's category.
     * @param description The command's description.
     * @param arguments This com.github.ArthurSchiavom.old.commands arguments as "[param1] [param2] ...". Can be null.
     * @param runInNewThread If the command actions should run on a new thread.
     */
    public CommandWithoutSubCommands(Category category, String description, String arguments, boolean runInNewThread) {
        super(category, description, runInNewThread);
        initialize(arguments);
    }

    /**
     * Initializes this class.
     *
     * @param arguments This command's arguments.
     */
    private void initialize(String arguments) {
        if (arguments == null)
            this.arguments = "";
        else
            this.arguments = arguments;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void buildHelpMessage() {
        /*
        EMBEDDED

        *full name [arg1] [arg2]
        description

        *examples
        example1
        example2

        *aliases
        alias1, alias2

        Footer: Random fact about the bot or something else
         */
        MessageCreateBuilder messageBuilder = new MessageCreateBuilder();
        EmbedBuilder eb = new EmbedBuilder();

        setHelpEmbedHeader(eb);
        this.setHelpEmbedExamples(eb, examples);
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
        StringBuilder header = new StringBuilder();
        header.append(this.calcFullNameWithPrefix()).append(" *").append(arguments)
                .append(Utils.getInvisibleCharacter()).append("*");
        eb.addField(header.toString(), "**" + this.getDescription() + "**", false);
    }

    /**
     * Adds an example.
     *
     * @param args The arguments for the command.
     * @param description The description for these arguments.
     */
    protected final void addExample(String args, String description) {
        examples.add("`" + this.calcFullNameWithPrefix() + " " + args + "` - " + description);
    }
}
