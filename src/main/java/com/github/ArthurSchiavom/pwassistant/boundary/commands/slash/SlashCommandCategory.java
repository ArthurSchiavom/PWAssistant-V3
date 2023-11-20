package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash;

import lombok.Getter;

public enum SlashCommandCategory {
    PWI_KINGDOM("💖 PWI Kingdom"),
    PWI("🎮 PWI"),
    INFO("❓ Information"),
    FUN("\uD83D\uDE04 Fun"),
    ADMIN("\uD83D\uDEE0\uFE0F Administration");

    @Getter
    private final String displayName;

    SlashCommandCategory(final String displayName) {
        this.displayName = displayName;
    }
}
