package com.spring_app.demo.utils;

import java.time.*;

public class BusinessHoursUtil {

    private static final LocalTime OPENING_TIME = LocalTime.of(9, 0); // 09:00
    private static final LocalTime CLOSING_TIME = LocalTime.of(18, 0); // 18:00

    public static boolean isOpeningHours(Instant instant) {
        LocalTime time = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalTime();

        return !time.isBefore(OPENING_TIME) && !time.isAfter(CLOSING_TIME);
    }

    public static String getDayOfWeek(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);

        return zonedDateTime.getDayOfWeek().toString();
    }
}