package com.lmsu.utils;

import com.lmsu.task.DailyTask;
import com.lmsu.task.WeeklyTask;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class ScheduleHelper implements Serializable {

    public static void dailyTask() {
        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);      //9h
        calendar.set(Calendar.MINUTE, 45);          //45m
        calendar.set(Calendar.SECOND, 0);           //00s
        calendar.set(Calendar.MILLISECOND, 0);      //00ms
        Date dateSchedule = calendar.getTime();
        long period = 24 * 60 * 60 * 1000;          //1 day

        DailyTask dailyTask = new DailyTask();
        timer.schedule(dailyTask, dateSchedule, period);  //repeat task at 9:00:00'00 everyday
    }

    public static void weeklyTask() {
        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);        //Monday
        calendar.set(Calendar.HOUR_OF_DAY, 9);                      //9h
        calendar.set(Calendar.MINUTE, 45);                          //45m
        calendar.set(Calendar.SECOND, 0);                           //00s
        calendar.set(Calendar.MILLISECOND, 0);                      //00ms
        Date dateSchedule = calendar.getTime();
        long period = 7 * 24 * 60 * 60 * 1000;                      //7 day

        WeeklyTask weeklyTask = new WeeklyTask();
        timer.schedule(weeklyTask, dateSchedule, period);  //repeat task at 9:00:00'00 every Monday
    }
}

