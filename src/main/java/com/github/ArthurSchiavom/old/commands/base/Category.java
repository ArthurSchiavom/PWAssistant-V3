package com.github.ArthurSchiavom.old.commands.base;

public enum Category implements Comparable<Category> {
	MODERATION("TO MODIFY!! Moderation")
	,INFORMATION("❓ Information")
	, FUN("\uD83D\uDE04 Fun")
	, UTILITY("\uD83D\uDD27 Utility")
	, PWI("\uD83C\uDFAE PWI")
	, CONFIGURATION("⚙ Configuration")
	, MUSIC("\uD83C\uDFB6 Music");

	private final String name;
	Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
