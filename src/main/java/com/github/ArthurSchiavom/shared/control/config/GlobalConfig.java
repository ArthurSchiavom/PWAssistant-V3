package com.github.ArthurSchiavom.shared.control.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Getter
public class GlobalConfig {
    @ConfigProperty(name = "discord.mainserver.id")
    Long mainServerId;
    @ConfigProperty(name = "discord.mainserver.welcome.channelid")
    Long welcomeChannelId;

    @ConfigProperty(name = "discord.mainserver.role.etherblade.id")
    Long roleIdEtherblade;
    @ConfigProperty(name = "discord.mainserver.role.twilighttemple.id")
    Long roleIdTwilightTemple;
    @ConfigProperty(name = "discord.mainserver.role.tideswell.id")
    Long roleIdTideswell;
    @ConfigProperty(name = "discord.mainserver.role.dawnglory.id")
    Long roleIdDawnglory;

    @ConfigProperty(name = "discord.mainserver.role.freegames.id")
    Long roleIdFreeGames;

    @ConfigProperty(name = "discord.botowner.id")
    Long botOwnerId;

    @Getter
    @ConfigProperty(name = "testmode")
    boolean isTestBot;
}
