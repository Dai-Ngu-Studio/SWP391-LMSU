package com.lmsu.task;

import java.io.Serializable;
import java.util.TimerTask;

public class WeeklyTask extends TimerTask implements Serializable {
    public void notifyNewArrivalBooks() {

    }

    public void notifyHighestRatedBookOfTheWeek() {

    }

    @Override
    public void run() {
        notifyNewArrivalBooks();
        notifyHighestRatedBookOfTheWeek();
    }
}
