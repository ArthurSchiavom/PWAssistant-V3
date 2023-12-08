package com.github.ArthurSchiavom.shared.control.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    /**
     * Gets the updateNextExecutionTime week day of the list given.
     *
     * @param daysOfWeek The days of the week according to the class Calendar.
     * @return The closest updateNextExecutionTime day of the week that matches one of the list.
     */
    public static Calendar getNextDayOfWeek(List<Integer> daysOfWeek, boolean includeToday, int hour, int minute) {
        Calendar timeNow = Calendar.getInstance();
        Calendar timeResult = Calendar.getInstance();
        timeResult.set(Calendar.HOUR_OF_DAY, hour);
        timeResult.set(Calendar.MINUTE, minute);
        timeResult.set(Calendar.SECOND, 0);
        timeResult.set(Calendar.MILLISECOND, 0);

        int dayToday = timeNow.get(Calendar.DAY_OF_MONTH);
        if (includeToday && daysOfWeek.contains(dayToday) && timeResult.after(timeNow))
            return timeResult;

        do {
            timeResult.add(Calendar.DAY_OF_MONTH, 1);
        } while (!daysOfWeek.contains(timeResult.get(Calendar.DAY_OF_WEEK)));
        return timeResult;
    }


    /**
     * Gets the updateNextExecutionTime day of the month (excluding today) that is present in the list.
     * <br><b>Days bigger than the biggest day of a month are converted to the biggest day of the month.</b>
     *
     * @param targetDays The target days.
     * @param hour       The hour to set in the result.
     * @param minute     The minute to set in the result.
     * @return The resulting Calendar.
     */
    public static Calendar getNextDayOfMonth(List<Integer> targetDays, boolean includeToday, int hour, int minute) {
        if (targetDays.isEmpty())
            throw new IllegalArgumentException("The list of days is empty.");

        Calendar timeNow = Calendar.getInstance();
        Calendar timeResult = Calendar.getInstance();
        timeResult.set(Calendar.HOUR_OF_DAY, hour);
        timeResult.set(Calendar.MINUTE, minute);
        timeResult.set(Calendar.SECOND, 0);
        timeResult.set(Calendar.MILLISECOND, 0);

        int dayToday = timeNow.get(Calendar.DAY_OF_MONTH);
        if (includeToday && targetDays.contains(dayToday) && timeResult.after(timeNow))
            return timeResult;

        int dayChosenThisMonth = -1;
        int biggestDayThisMonth = timeResult.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> targetDaysThisMonth = limitBiggestNumberInList(targetDays, biggestDayThisMonth);
        Collections.sort(targetDaysThisMonth);
        int currentDay = timeResult.get(Calendar.DAY_OF_MONTH);
        for (Integer targetDay : targetDaysThisMonth) {
            if (targetDay > currentDay && targetDay <= biggestDayThisMonth) {
                dayChosenThisMonth = targetDay;
                break;
            }
        }

        if (dayChosenThisMonth != -1) {
            timeResult.set(Calendar.DAY_OF_MONTH, dayChosenThisMonth);
        } else {
            timeResult.add(Calendar.MONTH, 1);
            int biggestDayNextMonth = timeResult.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<Integer> targetDaysNextMonth = limitBiggestNumberInList(targetDays, biggestDayNextMonth);
            Collections.sort(targetDaysNextMonth);
            timeResult.set(Calendar.DAY_OF_MONTH, targetDaysNextMonth.get(0));
        }
        return timeResult;
    }

    /**
     * Calculates a list with all the elements bigger than X replaced by X.
     *
     * @param list          The target list.
     * @param biggestNumber X.
     * @return The calculated list.
     */
    private static List<Integer> limitBiggestNumberInList(List<Integer> list, int biggestNumber) {
        List<Integer> result = new ArrayList<>(list);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) > biggestNumber) {
                result.remove(i);
                result.add(i, biggestNumber);
            }
        }
        return result;
    }
}
