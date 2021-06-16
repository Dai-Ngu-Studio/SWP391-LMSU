package com.lmsu.utils;

import java.io.Serializable;

public class DateHelpers implements Serializable {

    public static final java.sql.Date getCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date currentDateSQL = new java.sql.Date(currentDate.getTime());
        return currentDateSQL;
    }
}
