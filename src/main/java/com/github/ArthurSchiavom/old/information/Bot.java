package com.github.ArthurSchiavom.old.information;

import net.dv8tion.jda.api.JDA;

public class Bot {
	private static String game;
	private static JDA jdaInstance;
	private static String token;
	private static boolean isTestAccount;

	public static void setToken(String token) {
		Bot.token = token;
		isTestAccount = token.startsWith("Mzkz");
	}

	public static void setGame(String game) {
		Bot.game = game;
	}

	public static void setJdaInstance(JDA jdaInstance) {
		Bot.jdaInstance = jdaInstance;
	}

	public static String getGame() {
		return game;
	}

	public static JDA getJdaInstance() {
		return jdaInstance;
	}

	public static boolean isTestAccount() {
		return isTestAccount;
	}
}
