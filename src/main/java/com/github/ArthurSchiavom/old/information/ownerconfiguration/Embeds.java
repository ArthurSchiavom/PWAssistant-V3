package com.github.ArthurSchiavom.old.information.ownerconfiguration;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * Contains the owner configuration for embeds.
 */
public class Embeds {
	private static Color defaultEmbedColor;
	private static String helpEmbedFooterImageUrl;
	private static String helpEmbedFooterText;

	private Embeds() {}

	public static void setDefaultEmbedColor(Color defaultEmbedColor) {
		Embeds.defaultEmbedColor = defaultEmbedColor;
	}

	public static void setHelpEmbedFooterImageUrl(String helpEmbedFooterImageUrl) {
		Embeds.helpEmbedFooterImageUrl = helpEmbedFooterImageUrl;
	}

	public static void setHelpEmbedFooterText(String helpEmbedFooterText) {
		Embeds.helpEmbedFooterText = helpEmbedFooterText;
	}

	public static void configDefaultEmbedColor(EmbedBuilder eb) {
		if (defaultEmbedColor != null)
			eb.setColor(defaultEmbedColor);
	}

	public static void configHelpEmbedFooter(EmbedBuilder eb) {
		if (helpEmbedFooterImageUrl != null && helpEmbedFooterText != null)
			eb.setFooter(helpEmbedFooterText, helpEmbedFooterImageUrl);
		else if (helpEmbedFooterText != null)
			eb.setFooter(helpEmbedFooterText);
	}
}
