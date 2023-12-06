package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@Getter
public class SlashCommandInfo implements Comparable<SlashCommandInfo> {
    final private SlashCommandPath path;
    final private String description;
    final private boolean guildOnly;
    final private List<OptionData> options;
    final private SlashCommandCategory category;
    final private DefaultMemberPermissions defaultMemberPermissions;

    public SlashCommandInfo(SlashCommandPath path, String description, boolean guildOnly, List<OptionData> options, SlashCommandCategory category) {
        this.path = path;
        this.description = description;
        this.guildOnly = guildOnly;
        this.options = options;
        this.category = category;
        defaultMemberPermissions = DefaultMemberPermissions.ENABLED;
    }

    public SlashCommandInfo(SlashCommandPath path, String description, boolean guildOnly, List<OptionData> options, SlashCommandCategory category, DefaultMemberPermissions defaultMemberPermissions) {
        this.path = path;
        this.description = description;
        this.guildOnly = guildOnly;
        this.options = options;
        this.category = category;
        this.defaultMemberPermissions = defaultMemberPermissions;
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    public boolean isSubcommand() {
        return path.getSubCommandName() != null;
    }

    public boolean isNotSubcommand() {
        return path.getSubCommandName() == null;
    }

    @Override
    public int compareTo(SlashCommandInfo other) {
        return path.getFullPath().compareTo(other.path.getFullPath());
    }
}
