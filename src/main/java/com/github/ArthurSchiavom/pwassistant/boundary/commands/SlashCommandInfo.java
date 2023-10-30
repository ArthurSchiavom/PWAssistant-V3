package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@AllArgsConstructor
@Getter
public class SlashCommandInfo {
    private SlashCommandPath path;
    private String description;
    private boolean guildOnly;
    private List<OptionData> options;

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    public boolean isSubcommand() {
        return path.getSubCommandName() != null;
    }

    public boolean isNotSubcommand() {
        return path.getSubCommandName() == null;
    }
}
