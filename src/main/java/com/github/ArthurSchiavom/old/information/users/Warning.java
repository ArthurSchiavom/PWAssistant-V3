package com.github.ArthurSchiavom.old.information.users;

import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.Calendar;

public class Warning {
	private final String reason;
	private final Calendar time;

	public Warning(String reason, Calendar time) {
		this.reason = reason;
		this.time = time;
	}

	public Warning(Warning warning) {
		this.reason = warning.getReason();
		this.time = warning.getTime();
	}

	public String getReason() {
		return reason;
	}

	public Calendar getTime() {
		return (Calendar) time.clone();
	}

	public String getTimeDisplay() {
		return Utils.calendarToDateTimeDisplay(time);
	}
}
