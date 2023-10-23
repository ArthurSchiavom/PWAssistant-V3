package com.github.ArthurSchiavom.pwassistant.entity;

import com.github.ArthurSchiavom.shared.control.config.GlobalConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public enum PwiServer {
	ETHERBLADE("Etherblade", "ET", "PST")
	, TWILIGHT_TEMPLE("Twilight Temple", "TT", "PST")
	, DAWNGLORY("Dawnglory", "DA", "CET")
	, TIDESWELL("Tideswell", "TI", "America/New_York");

	private final String name;
	private final String shortName;
	private final String timezone;
	PwiServer(String name, String shortName, String timezone) {
		this.name = name;
		this.shortName = shortName;
		this.timezone = timezone;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getTimezoneString() {
		return timezone;
	}


	/**
	 * Finds a server mentioned in a String.
	 *
	 * @param string The string to analyze.
	 * @return (1) The enum item matching the name or
	 * <br>(2) <b>null</b> if no item matches the name.
	 */
	public static PwiServer fromString(String string) {
		string = string.toLowerCase();
		PwiServer server = null;
		if (string.contains("da"))
			server = DAWNGLORY;
		else if (string.contains("et"))
			server = ETHERBLADE;
		else if (string.contains("tw") || string.contains("tt"))
			server = TWILIGHT_TEMPLE;
		else if (string.contains("ti"))
			server = TIDESWELL;
		return server;
	}


	/**
	 * Finds servers mentioned in a String.
	 *
	 * @param string The string to analyze.
	 * @return The enum items matching the name.
	 */
	public static List<PwiServer> multipleFromString(String string) {
		string = string.toLowerCase();
		List<PwiServer> pwiServers = new ArrayList<>();
		if (string.contains("da"))
			pwiServers.add(DAWNGLORY);
		if (string.contains("tw") || string.contains("tt"))
			pwiServers.add(TWILIGHT_TEMPLE);
		if (string.contains("et"))
			pwiServers.add(ETHERBLADE);
		if (string.contains("ti"))
			pwiServers.add(TIDESWELL);
		return pwiServers;
	}

	public Long getId(GlobalConfig config) {
		switch (this) {
			case DAWNGLORY -> {
				return config.getRoleIdDawnglory();
			}
			case TIDESWELL -> {
				return config.getRoleIdTideswell();
			}
			case ETHERBLADE -> {
				return config.getRoleIdEtherblade();
			}
			case TWILIGHT_TEMPLE -> {
				return config.getRoleIdTwilightTemple();
			}
			default -> {
				throw new UnsupportedOperationException();
			}
		}
	}

	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone(timezone);
	}

	public Calendar getCurrentTime() {
		return Calendar.getInstance(getTimeZone());
	}
}
