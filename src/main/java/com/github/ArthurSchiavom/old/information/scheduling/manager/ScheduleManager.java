package com.github.ArthurSchiavom.old.information.scheduling.manager;

import com.github.ArthurSchiavom.old.utils.Utils;
import com.github.ArthurSchiavom.pwassistant.entity.RepetitionType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleManager {
	private final RepetitionType repetitionType;
	private final List<Integer> targetDays;
	private final int hour;
	private final int minute;
	private Calendar nextExecutionTime;

	public ScheduleManager(RepetitionType repetitionType, List<Integer> targetDays, int hour, int minute) {
		this.repetitionType = repetitionType;
		this.targetDays = new ArrayList<>(targetDays);
		this.hour = hour;
		this.minute = minute;
		nextExecutionTime = repetitionType.getNext(targetDays, hour, minute);
	}

	public RepetitionType getRepetitionType() {
		return repetitionType;
	}

	public List<Integer> getTargetDays() {
		return new ArrayList<>(targetDays);
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public boolean isPastExecutionTime(Calendar currentTime) {
		return currentTime.after(nextExecutionTime);
	}

	/**
	 * @return The next execution time.
	 */
	public Calendar getNextExecutionTime() {
		return nextExecutionTime;
	}

	/**
	 * Updates the next execution time. (Or remains unchanged if the current execution time is still in the future, given that all parameters remain unchanged)
	 */
	public void updateNextExecutionTime() {
		nextExecutionTime = repetitionType.getNext(targetDays, hour, minute);
	}

	public String getTargetDaysFullDisplay() {
		List<String> daysFullDisplay;
		switch (repetitionType) {
			case WEEKLY:
				daysFullDisplay = getWeekDaysFullDisplay();
				break;
			case MONTHLY:
				daysFullDisplay = Utils.integerListToStringList(targetDays);
				break;
			default:
				daysFullDisplay = null;
		}
		return Utils.getListdDisplayCommaSeparated(daysFullDisplay);
	}

	public List<String> getWeekDaysFullDisplay() {
		List<String> fullDisplayList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Integer day : targetDays) {
			cal.set(Calendar.DAY_OF_WEEK, day);
			fullDisplayList.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.ENGLISH));
		}
		return fullDisplayList;
	}
}
