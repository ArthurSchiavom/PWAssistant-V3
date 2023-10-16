package com.github.ArthurSchiavom.old.utils;

public class Security {
	public static boolean stringIncludesAdvertisement(String str) {
		return str.toLowerCase().contains("discord.gg/") || str.toLowerCase().contains("twitch.tv/");
	}

	/**
	 * Verifies if an username is suspicious. The following are considered suspicious patterns:
	 * <br>1. Ends in numbers.
	 *
	 * @param username The username to analyse.
	 * @return If the username is suspicious.
	 */
	public static boolean isUsernameSuspicious(String username) {
		return endsInNumbers(username);
	}

	private static boolean endsInNumbers(String str) {
		String lastDigits = str.substring(str.length()-2);
		try {
			Integer.parseInt(lastDigits);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
