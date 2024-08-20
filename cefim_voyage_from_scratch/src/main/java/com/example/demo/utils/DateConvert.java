package com.example.demo.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConvert {

    /**
     * convert Date => LocalDateTime
     * @param dateToConvert
     * @return LocalDateTime
     */
    public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    /**
     * convert Date => LocalDate
     * @param dateToConvert
     * @return LocalDate
     */
    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return LocalDate.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    /**
     * convert LocalDateTime => Date
     * @param dateToConvert
     * @return Date
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * convert LocalDate => Date
     * @param dateToConvert
     * @return Date
     */
    public static Date convertLocalDateToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
