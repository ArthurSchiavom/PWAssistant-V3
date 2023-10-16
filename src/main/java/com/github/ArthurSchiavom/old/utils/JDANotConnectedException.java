package com.github.ArthurSchiavom.old.utils;

/**
 * This means that the JDA is not connected to Discord.
 */
public class JDANotConnectedException extends Exception {
	public JDANotConnectedException(String message) {
		super(message);
	}
}
