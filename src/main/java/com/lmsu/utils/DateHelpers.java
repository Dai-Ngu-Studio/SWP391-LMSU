package com.lmsu.utils;

import java.io.Serializable;
import java.time.LocalDate;

public class DateHelpers implements Serializable {

    public static java.sql.Date getCurrentDate() {
        return java.sql.Date.valueOf(LocalDate.now());
    }

    public static java.sql.Date getDeadlineDate(java.sql.Date date, long days) {
        LocalDate locDate = date.toLocalDate();
        locDate = locDate.plusDays(days);
        return java.sql.Date.valueOf(locDate);
    }
}
