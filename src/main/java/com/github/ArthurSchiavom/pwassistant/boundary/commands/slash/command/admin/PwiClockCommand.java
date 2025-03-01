package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.admin;

import com.github.ArthurSchiavom.pwassistant.boundary.BoundaryConfig;
import com.github.ArthurSchiavom.pwassistant.boundary.JdaProvider;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.choices.PwiServerChoices;
import com.github.ArthurSchiavom.pwassistant.control.pwi.PwiServerService;
import com.github.ArthurSchiavom.pwassistant.entity.PwiClock;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.ArthurSchiavom.pwassistant.boundary.utils.ChoiceUtils.getChoiceAsString;

@ApplicationScoped
@Slf4j
public class PwiClockCommand implements SlashCommand {
    private static final String SUBCOMMAND_NAME = "clock";
    private static final String DESCRIPTION = "Create a live clock that shows the time in PWI servers. (max 10 per server) (admin only)";
    private static final String OPTION_NAME_SERVER1 = "server1";
    private static final String OPTION_NAME_SERVER2 = "server2";
    private static final String OPTION_NAME_SERVER3 = "server3";
    private static final String OPTION_NAME_SERVER4 = "server4";
    private static final Integer MAX_FAILURES = 60 * 24; // 1 day in minutes
    private final PwiServerChoices pwiServerChoices = new PwiServerChoices();

    private final Map<PwiClock, Integer> updateFailureCounter = new HashMap<>();

    @Inject
    PwiServerService serverService;
    @Inject
    JdaProvider jdaProvider;
    @Inject
    GlobalConfig globalConfig;

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.CREATE, SlashCommandSubgroups.PWI, SUBCOMMAND_NAME),
                DESCRIPTION,
                true,
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_SERVER1, "server1 (or leave empty to create for all servers)", false, true),
                        new OptionData(OptionType.STRING, OPTION_NAME_SERVER2, "server2", false, true),
                        new OptionData(OptionType.STRING, OPTION_NAME_SERVER3, "server3", false, true),
                        new OptionData(OptionType.STRING, OPTION_NAME_SERVER4, "server4", false, true)),
                SlashCommandCategory.PWI,
                DefaultMemberPermissions.enabledFor(Permission.MESSAGE_MANAGE));
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        final Set<PwiServer> servers = Stream.of(pwiServerChoices.getOptionObjectFromPayload(event, OPTION_NAME_SERVER1),
                        pwiServerChoices.getOptionObjectFromPayload(event, OPTION_NAME_SERVER2),
                        pwiServerChoices.getOptionObjectFromPayload(event, OPTION_NAME_SERVER3),
                        pwiServerChoices.getOptionObjectFromPayload(event, OPTION_NAME_SERVER4))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (servers.isEmpty()) {
            servers.addAll(PwiServer.allValues());
        }

        final MessageChannel channel = event.getChannel();
        final long guildId = event.getGuild().getIdLong();
        final long channelId = channel.getIdLong();
        final MessageEmbed loadingClockEmbed = new EmbedBuilder()
                .setTitle("【ＣＯＯＬ　ＣＬＯＣＫ　ＬＯＡＤＩＮＧ】")
                .setDescription("This can take up to a minute.")
                .build();
        event.reply("creating").setEphemeral(true).queue();
        event.getChannel().sendMessageEmbeds(loadingClockEmbed).queue(msg -> {
            final long msgId = msg.getIdLong();
            final PwiClock newCLock = new PwiClock()
                    .setServerId(guildId)
                    .setChannelId(channelId)
                    .setMessageId(msgId)
                    .setServers(servers);
            serverService.registerPwiClock(newCLock);
        });
    }

    @Override
    public List<Command.Choice> getAutoCompletion(CommandAutoCompleteInteractionEvent event) {
        String lastUserArg = getChoiceAsString(event, OPTION_NAME_SERVER4);
        if (lastUserArg == null) {
            lastUserArg = getChoiceAsString(event, OPTION_NAME_SERVER3);
        }
        if (lastUserArg == null) {
            lastUserArg = getChoiceAsString(event, OPTION_NAME_SERVER2);
        }
        if (lastUserArg == null) {
            lastUserArg = getChoiceAsString(event, OPTION_NAME_SERVER1);
        }
        if (lastUserArg == null) {
            lastUserArg = "";
        }
        return pwiServerChoices.getAutocompleteChoices(lastUserArg);
    }

    // At every minute from 0 through 59
    @Scheduled(cron = "0 0-59 * * * ?", delay = 60L, delayUnit = TimeUnit.SECONDS)
    void updateClocks() {
        if (globalConfig.isTestBot()) {
            return;
        }

        final JDA jda = jdaProvider.getJda();
        final Map<Set<PwiServer>, MessageEditData> messages = new HashMap<>();
        final List<PwiClock> clocks = serverService.getAllPwiClocks();
        for (final PwiClock clock : clocks) {
            final Set<PwiServer> servers = clock.getServers();
            MessageEditData updatedClock = messages.get(servers);
            if (updatedClock == null) {
                updatedClock = MessageEditData.fromEmbeds(buildClock(servers));
                messages.put(servers, updatedClock);
            }
            Guild guild = jda.getGuildById(clock.getServerId());
            if (guild == null) {
                processFailure(clock);
                return;
            }
            TextChannel channel = guild.getTextChannelById(clock.getChannelId());
            if (channel == null) {
                processFailure(clock);
                return;
            }

            channel.editMessageById(clock.getMessageId(), updatedClock)
                    .queue(
                            msg -> updateFailureCounter.remove(clock),
                            exception -> processFailure(clock));
        }
    }

    private void processFailure(PwiClock clock) {
        Integer failureCount = updateFailureCounter.getOrDefault(clock, 0) + 1;
        updateFailureCounter.put(clock, failureCount);
        if (failureCount > MAX_FAILURES) {
            serverService.removePwiClock(clock);
        }
        log.warn("Clock ID {} failure count: {}/{}", clock.getId(), failureCount, MAX_FAILURES);
    }

    private MessageEmbed buildClock(final Set<PwiServer> servers) {

        final StringBuilder descriptionSb = new StringBuilder();
        for (PwiServer server : servers) {
            final Calendar serverTime = server.getCurrentTime();
            descriptionSb.append("\n\n**").append(server.getName()).append("**: ")
                    .append(String.format("%tR", serverTime));
        }

        final EmbedBuilder eb = new EmbedBuilder()
                .setTitle(":clock1: PWI Clock")
                .setDescription(descriptionSb.toString())
                .setColor(BoundaryConfig.DEFAULT_EMBED_COLOR);

        return eb.build();
    }
}
