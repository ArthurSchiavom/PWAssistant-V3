package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash;

import lombok.Getter;

public enum SlashCommandCategory {
    INFO("❓ Information"), PWI("🎮 PWI"), PWI_KINGDOM("💖 PWI Kingdom"), FUN("\uD83D\uDE04 Fun");

    @Getter
    private final String displayName;

    SlashCommandCategory(final String displayName) {
        this.displayName = displayName;
    }
}
