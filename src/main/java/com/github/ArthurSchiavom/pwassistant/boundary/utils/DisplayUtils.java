package com.github.ArthurSchiavom.pwassistant.boundary.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DisplayUtils {
    public static String formatCalendar(final Calendar cal) {
        return String.format("%tR", cal);
    }
}
