package com.github.ArthurSchiavom.old.information.ownerconfiguration;

/**
 * Contains the owner configuration for com.github.ArthurSchiavom.old.commands.
 */
public class Commands {
	private static String commandPrefix;
	private static int prefixNChars;

	private Commands() {}

	/**
	 * Sets the prefix and it's length configurations.
	 *
	 * @param prefix The prefix to set.
	 */
	public static void setPrefix(String prefix) {
		commandPrefix = prefix;
		prefixNChars = prefix.length();
	}

	public static String getCommandPrefix() {
		return commandPrefix;
	}

	public static int getPrefixNChars() {
		return prefixNChars;
	}
}
