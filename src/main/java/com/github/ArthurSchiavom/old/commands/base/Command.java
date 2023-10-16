package com.github.ArthurSchiavom.old.commands.base;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Channels;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Commands;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A bot command with multiple names, help message and usage requirements.
 */
public abstract class Command {
    //private static final String INDIVIDUAL_COMMAND_HELP_THUMBNAIL = "https://live.staticflickr.com/65535/48673204931_a08f912d71_o_d.png";

    private Command superCommand = null;
    private Category category;
    private List<String> names = new ArrayList<>();
    private List<String> fullNames = new ArrayList<>();
    private List<String> fullNamesLowerCase = new ArrayList<>();
    private final boolean runInNewThread;
    private final String description;
    private final RequirementsManager requirementsManager = new RequirementsManager();
    private MessageCreateData helpMessage = MessageCreateData.fromContent("Help was not defined for this command.");

    /**
     * Creates a new command.
     *
     * @param category The command's category.
     * @param description The command's description.
     * @param runInNewThread If the command actions should run on a new thread.
     */
    public Command(Category category, String description, boolean runInNewThread) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Null or empty argument.");
        }
        this.category = category;
        this.description = description;
        this.runInNewThread = runInNewThread;
    }

    /**
     * Creates a new command.
     *
     * @param category The command's category.
     * @param description The command's description.
     * @param runInNewThread If the command actions should run on a new thread.
     * @param superCommand This command's super command.
     */
    public Command(Category category, String description, boolean runInNewThread, Command superCommand) {
        this(category, description, runInNewThread);
        if (superCommand == null) {
            throw new IllegalArgumentException("Null or empty argument.");
        }
        this.superCommand = superCommand;
    }

    //<editor-fold desc="Getters Setters">

    /**
     * @return This command's category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @return This command's super-command or
     * <br>null if there is no super-command.
     */
    public Command getSuperCommand() {
        return superCommand;
    }

    /**
     * @return This command's names.
     */
    public List<String> getNames() {
        return new ArrayList<>(names);
    }

    /**
     * @return This command's name. (the first name added)
     */
    public String getName(){
        return names.get(0);
    }

    /**
     * @return This command's fullNames.
     */
    public List<String> getFullNames() {
        return new ArrayList<>(fullNames);
    }

    /**
     * @return This command's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return This command's requirements for running.
     */
    public RequirementsManager getRequirementsManager() {
        return requirementsManager;
    }

    /**
     * @return A message that contains com.github.ArthurSchiavom.old.information about this command or
     * <br>null if the help message was never built.
     */
    public MessageCreateData getHelpMessage() {
        return helpMessage;
    }

    /**
     * Defines this command's help message.
     *
     * @param helpMessage The help message.
     */
    protected void setHelpMessage(MessageCreateData helpMessage) {
        this.helpMessage = helpMessage;
    }
    //</editor-fold>

    /**
     * Adds a command name and consequent fullnames. Names are used in the menus while fullnames are used to verify if a string is a command usage.
     * <br>
     * <br><b>Example</b>
     * <br>Super command "Game" with sub-command "add"
     * <br>Full command name: Game add
     * <br>Command name: add
     * <br>addName("add") will register the name "add" and the full name "Game add"
     * given that "Game" is the super command's name.
     *
     * @param name The name to register.
     */
    protected void addName(String name) {
        names.add(name);
        if (superCommand == null) {
            fullNames.add(name);
            fullNamesLowerCase.add(name.toLowerCase());
        }
        else {
            for(String fullSuperCommandName : superCommand.getFullNames()) {
                String fullName = fullSuperCommandName + " " + name;
                fullNames.add(fullName);
                fullNamesLowerCase.add(fullName.toLowerCase());
            }
        }
    }

    /**
     * Adds a command fullname. Fullnames are used to verify if a string is a command usage.
     * <br>
     * <br><b>Example</b>
     * <br>Super command "Game" with sub-command "add"
     * <br>Full command name: Game add
     * <br>Command name: add
     * <br>addFullName("add") will register the full name "Game add" but not the name "add".
     *
     * @param newName The name to register.
     */
    private final void addFullName(String newName) {
        fullNames.add(newName);
    }

    /**
     * Verifies if this command can be run for the given event and runs it.
     * <br>After running, the message is deleted.
     *
     * @param event The event that requested this command.
     */
    public final void run(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;

        if (!requirementsManager.meetsRequirements(event)) {
            return;
        }

        if (!runInNewThread)
            runCommandActions(event);
        else {
            new Thread(() -> runCommandActions(event)).start();
        }
        Channels.getBotLogChannel().sendMessage("**" + this.fullNames.get(0) + "** used at **" + event.getGuild().getName() + "**").queue();
    }

    /**
     * Executes this command actions.
     *
     * @param event The event that requested this command.
     */
    protected abstract void runCommandActions(MessageReceivedEvent event);

    /**
     * Verifies if the given string is a call to this command.
     *
     * @param messageLowerCaseNoPrefix The message to verify, in lowercase and without prefix.
     *                                 <br>This avoids processing the same message repeatedly for multiple com.github.ArthurSchiavom.old.commands.
     * @return The command that the message refers to if it is associated to this command or
     * <br>null otherwise.
     */
    public Command isThisCommand(String messageLowerCaseNoPrefix) {
        for (String fullName : this.getFullNames()) {
            String fullNameLC = fullName.toLowerCase();
            if (messageLowerCaseNoPrefix.equals(fullNameLC)
                    || messageLowerCaseNoPrefix.startsWith(fullNameLC + " "))
                return this;
        }

        return null;
    }

    /**
     * Builds the help message based on the current com.github.ArthurSchiavom.old.information stored.
     */
    public abstract void buildHelpMessage();

    /**
     * @return The amount of com.github.ArthurSchiavom.old.commands involved in the chain of super com.github.ArthurSchiavom.old.commands.
     * <br>Example: The command "!trigger register fixed [arguments...]" has 3 com.github.ArthurSchiavom.old.commands in the chain: "trigger", "register" and "fixed".
     */
    protected int getNArgumentsInChain() {
        return fullNames.get(0).substring(Commands.getPrefixNChars()).split(" ").length;
    }

    /**
     * Calculates the arguments in a message.
     * <br>Here, each argument is, by priority:
     * <br>1. A sequence of text surrounded by double quotes.
     * <br>2. A sequence of text surround by spaces.
     *
     * @param args The arguments.
     * @return The arguments of the raw message that generated this event.
     */
    public ArrayList<String> getIndividualArguments(String args) {
        throw new UnsupportedOperationException("Only implement this if you ever plan on using it");
    }


    /**
     * Extracts the arguments only from a command issues..
     *
     * @param command The command issued.
     * @return Only the arguments from the command issued or
     * <br>null if there are no arguments.
     */
    public String extractArgumentsOnly(String command) {
        return removeArgumentsAndPrefix(command, getNArgumentsInChain());
    }

    /**
     * Removes the prefix and n arguments from a message.
     *
     * @param msg The target message.
     * @param nArguments The number of arguments to unregister.
     * @return The message with the prefix and arguments removed or
     * <br>null if there are no arguments left.
     */
    private String removeArgumentsAndPrefix(String msg, int nArguments) {
        msg = removePrefix(msg);
        String[] split = msg.split(" ", nArguments+1);
        if (split.length <= nArguments)
            return null;
        else
            return split[nArguments];
    }

    /**
     * Removes the prefix from a message. If the message doesn't contain the prefix, still removes the amount of characters from the beginning of the text.
     *
     * @param msg The message to unregister prefix from.
     * @return The message without the prefix.
     */
    private String removePrefix(String msg) {
        return msg.substring(Commands.getPrefixNChars());
    }

    /**
     * Calculates the full main (first) name of this command with the prefix.
     *
     * @return The name calculated.
     */
    public String calcFullNameWithPrefix() {
        if (fullNames.size() == 0) {
            return "Undefined";
        }
        return Commands.getCommandPrefix() + fullNames.get(0);
    }

//    /**
//     * Defines the command help embed footer.
//     *
//     * @param eb The target embed.
//     */
//    protected void setHelpEmbedThumbnail(EmbedBuilder eb) {
//        eb.setThumbnail(INDIVIDUAL_COMMAND_HELP_THUMBNAIL);
//    }

    /**
     * Defines the command help embed color.
     *
     * @param eb The target embed.
     */
    protected void setHelpEmbedColor(EmbedBuilder eb) {
        Embeds.configDefaultEmbedColor(eb);
    }

    /**
     * Defines the command help embed examples.
     *
     * @param eb The target embed.
     */
    protected void setHelpEmbedExamples(EmbedBuilder eb, List<String> examples) {
        String helpExamples = getListDisplay(examples);
        if (helpExamples != null) {
            eb.addField("Examples", helpExamples, false);
        }
    }

    /**
     * Calculates the textual display of a list of strings.
     * <br>Elements are displayed each in a different line.
     *
     * @param elements The target elements.
     * @return The calculated display text.
     */
    protected String getListDisplay(List<String> elements) {
        if (elements.size() > 0) {
            Iterator<String> elementsIt = elements.iterator();
            StringBuilder result = new StringBuilder();
            result.append(elementsIt.next());
            while (elementsIt.hasNext()) {
                result.append("\n").append(elementsIt.next());
            }
            return result.toString();
        }
        return null;
    }

    /**
     * Defines a help embed's aliases.
     *
     * @param eb The target embed.
     * @param names The aliases to set.
     */
    protected void setHelpEmbedAliases(EmbedBuilder eb, List<String> names) {
        String helpAliases = getHelpAliasesDisplay(names);
        if (helpAliases != null) {
            eb.addField("Aliases", helpAliases, false);
        }
    }

    /**
     * Calculates the textual display of aliases
     *
     * @param names The list of all the command's names.
     * @return The calculated text.
     */
    protected String getHelpAliasesDisplay(List<String> names) {
        if (names.size() > 1) {
            StringBuilder aliasesSb = new StringBuilder();
            aliasesSb.append(names.get(1));
            for (int i = 2; i < names.size(); i++) {
                aliasesSb.append(", ").append(names.get(i));
            }
            return aliasesSb.toString();
        }
        return null;
    }
}
