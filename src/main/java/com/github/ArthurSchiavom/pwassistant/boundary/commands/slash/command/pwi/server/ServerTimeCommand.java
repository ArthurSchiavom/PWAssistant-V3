//package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi.server;
//
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
//import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
//import jakarta.enterprise.context.ApplicationScoped;
//import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
//
//@ApplicationScoped
//public class ServerTimeCommand implements SlashCommand {
//    private static final String SUBCOMMAND_NAME = "time";
//    private static final String DESCRIPTION = "See the current time in PWI servers";
//
//    @Override
//    public SlashCommandInfo getSlashCommandInfo() {
//        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.SERVER, SUBCOMMAND_NAME),
//                DESCRIPTION,
//                true,
//                null,
//                SlashCommandCategory.PWI);
//    }
//
//    @Override
//    public void execute(final SlashCommandInteractionEvent event) {
//        event.reply("ok").setEphemeral(true).queue();
//        System.out.println("full " + event.getFullCommandName());
//        System.out.println("cmd " + event.getCommandString());
//        System.out.println("sub " + event.getSubcommandName());
//        System.out.println("group " + event.getSubcommandGroup());
//    }
//}
