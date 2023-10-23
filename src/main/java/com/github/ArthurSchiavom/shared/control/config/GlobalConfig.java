package com.github.ArthurSchiavom.shared.control.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GlobalConfig {
    @Getter
    @ConfigProperty(name = "discord.mainserver.id")
    Long mainServerId;
    @Getter
    @ConfigProperty(name = "discord.mainserver.role.etherblade.id")
    Long roleIdEtherblade;
    @Getter
    @ConfigProperty(name = "discord.mainserver.role.twilighttemple.id")
    Long roleIdTwilightTemple;
    @Getter
    @ConfigProperty(name = "discord.mainserver.role.tideswell.id")
    Long roleIdTideswell;
    @Getter
    @ConfigProperty(name = "discord.mainserver.role.dawnglory.id")
    Long roleIdDawnglory;
}
