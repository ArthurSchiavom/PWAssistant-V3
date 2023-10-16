package com.github.ArthurSchiavom.pwassistant.entity;

import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.Calendar;
import java.util.List;

public enum RepetitionType {
	WEEKLY("Weekly"), MONTHLY("Monthly");

	private final String displayName;
	RepetitionType(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

	public Calendar getNext(List<Integer> targetDays, int hour, int minute) {
		switch (this) {
			case WEEKLY:
				return getNextDayOfWeek(targetDays, hour, minute);
			case MONTHLY:
				return getNextDayOfMonth(targetDays, hour, minute);
			default:
				throw new UnsupportedOperationException(this.toString() + " is not configured internally.");
		}
	}

	private Calendar getNextDayOfMonth(List<Integer> targetDays, int hour, int minute) {
		Calendar cal = Utils.getNextDayOfMonth(targetDays, true, hour, minute);

		return cal;
	}

	private Calendar getNextDayOfWeek(List<Integer> targetDays, int hour, int minute) {
		Calendar cal = Utils.getNextDayOfWeek(targetDays, true, hour, minute);

		return cal;
	}

	public static RepetitionType fromString(String str) {
		for (RepetitionType type : RepetitionType.values()) {
			if (str.equalsIgnoreCase(type.toString()))
				return type;
		}
		return null;
	}
}
