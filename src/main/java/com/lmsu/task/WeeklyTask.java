package com.lmsu.task;

import java.io.Serializable;
import java.util.TimerTask;

public class WeeklyTask extends TimerTask implements Serializable {
    public static void notifyNewArrivalBooks() {

    }

    public static void notifyHighestRatedBookOfTheWeek() {

    }

    @Override
    public void run() {
        notifyNewArrivalBooks();
        notifyHighestRatedBookOfTheWeek();
    }
}
