package com.github.ArthurSchiavom.pwassistant.boundary.commands.validations;

import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ApplicationScoped
public class Validations {
    @Inject
    GlobalConfig config;

    public boolean usedInMainServer(final GenericCommandInteractionEvent event) {
        return event.getGuild() != null && event.getGuild().getIdLong() == config.getMainServerId();
    }
}
