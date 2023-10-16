package com.github.ArthurSchiavom.lifecycle.start;

import com.github.ArthurSchiavom.old.commands.CommandExecutor;
import com.github.ArthurSchiavom.old.database.base.DatabaseManager;
import com.github.ArthurSchiavom.old.events.EventsManager;
import com.github.ArthurSchiavom.old.information.Bot;
import com.github.ArthurSchiavom.old.information.admins.AdminsManager;
import com.github.ArthurSchiavom.old.information.clocks.ClockRegister;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.*;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems.PWIItems;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.roles.Roles;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import com.github.ArthurSchiavom.old.questionnaire.base.QuestionnaireRegister;
import com.github.ArthurSchiavom.old.timer.HalfMinutely;
import com.github.ArthurSchiavom.old.timer.Minutely;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import static com.github.ArthurSchiavom.old.information.ownerconfiguration.Database.databaseName;
import static com.github.ArthurSchiavom.old.information.ownerconfiguration.Database.databasePassword;
import static com.github.ArthurSchiavom.old.information.ownerconfiguration.Database.databaseUrl;
import static com.github.ArthurSchiavom.old.information.ownerconfiguration.Database.databaseUsername;

/**
 * Program boot-up operator.
 */
public class Bootstrap {
    private static final String CONFIG_FILE_NAME = "config.cfg";
    private static final String TOKEN_FILE_NAME = "token.cfg";
    private static final String PWI_ITEMS_FILE_NAME = "ItemIDList.cfg";

    private Bootstrap() {
    }

    /**
     * Boots the program up.
     *
     * @return if the loading was successful.
     */
    public static boolean load() {
        // NOTE: The order matters!
        System.out.println("Booting-up....");

        /** LOAD CONFIG FILES */
        String token = loadToken();

        if (token == null)
            return false;
        if (!launchJDA(token))
            return false;
        if (!loadConfigFile())
            return false;
        if (!loadPWIItems()) {
            return false;
        }
        /** LOAD CONFIG FILES */

        /** LOAD CLASS INFO/SINGLETONS */
        CommandExecutor.initialize();
        AdminsManager.initialize();
        Roles.initialize();
        ClockRegister.initialize();
        QuestionnaireRegister.initialize();
        TriggerRegister.initialize();
        /** LOAD CLASS INFO/SINGLETONS */

        /** LOAD DABATASE AND TABLES */
        if (!DatabaseManager.initialize())
            return false;
        /** LOAD DABATASE AND TABLES */

        /** LOAD TIMERS */
        new Minutely().start();
        new HalfMinutely().start();
        /** LOAD TIMERS */

        configJDA();
        System.out.println("Boot-up complete");
        return true;
    }

    /**
     * Loads the bot token into the memory.
     */
    public static String loadToken() {
        try (BufferedReader br = new BufferedReader(new FileReader(TOKEN_FILE_NAME))) {
            return br.readLine().trim();
        } catch (Exception e) {
            System.out.println("No token in the first line of the config file (or no config file).");
        }
        return null;
    }

    /**
     * Logs the bot into Discord.
     */
    private static boolean launchJDA(String botToken) {
        boolean success = true;

        try {
            Bot.setJdaInstance(JDABuilder.createDefault(loadToken(),
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.MESSAGE_CONTENT)
                    .build().awaitReady());
        } catch (Exception e) {
            System.out.println("FAILED TO LOG-IN THE BOT");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /**
     * TODO - should not be public :eyes:
     * Configures JDA by:
     * <br> * Setting the event listener
     * <br> * Setting the game that the bot is playing
     */
    private static void configJDA() {
        Bot.getJdaInstance().addEventListener(new EventsManager());
        setDefaultPlaying();
    }

    public static void setDefaultPlaying() {
        String game = Bot.getGame();
        if (game != null && !game.trim().isEmpty())
            Bot.getJdaInstance().getPresence().setActivity(Activity.playing(game));
    }

    /**
     * Loads the owner configuration file.
     */
    private static boolean loadConfigFile() {
        boolean success = true;
        int nLine = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILE_NAME))) {
            String str;
            while ((str = br.readLine()) != null) {
                nLine++;
                if (str.contains("=")) {
                    String[] config = str.split("=");
                    config[0] = config[0].toLowerCase().replace(" ", "");
                    config[1] = config[1].trim();
                    switch (config[0]) {
                        case "streamurl":
                            Misc.streamUrl = config[1];
                            break;
                        case "databaseurl":
                            databaseUrl = config[1];
                            break;
                        case "databasename":
                            databaseName = config[1];
                            break;
                        case "databaseusername":
                            databaseUsername = config[1];
                            break;
                        case "databasepassword":
                            databasePassword = config[1];
                            break;
                        case "commandsprefix":
                            Commands.setPrefix(getQuoteMarkedConfig(config[1]));
                            break;
                        case "helpembedcolor":
                            Embeds.setDefaultEmbedColor(Color.decode(config[1]));
                            break;
                        case "helpembedfooterimageurl":
                            Embeds.setHelpEmbedFooterImageUrl(config[1]);
                            break;
                        case "helpembedfootertext":
                            Embeds.setHelpEmbedFooterText(config[1]);
                            break;
                        case "mainguildid":
                            Guilds.mainGuildId = Long.parseLong(config[1]);
                            break;
                        case "ownerid":
                            Users.ownerId = Long.parseLong(config[1]);
                            break;
                        case "game":
                            Bot.setGame(config[1]);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR READING THE CONFIGURATION FILE. LINE " + nLine);
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private static String getQuoteMarkedConfig(String cfg) {
        return cfg.split("\"")[1];
    }

    private static boolean loadPWIItems() {
        String[] itemInfo;
        {
            try (BufferedReader br = new BufferedReader(new FileReader(PWI_ITEMS_FILE_NAME))) {

                for (int i = 0; i < 28701; i++) {
                    itemInfo = br.readLine().split(";");
                    PWIItems.addItem(itemInfo[0], itemInfo[1]);
                }
            } catch (Exception e) {
                System.out.println("FAILED TO LOAD PWI ITEMS");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
