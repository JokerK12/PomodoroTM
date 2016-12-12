package com.kk.pomodorotm.date;

/**
 * Created by Karol on 2016-08-26.
 */
public class Settings {

    //1 min = 60000
    private static long amountOfTaskTime = 6000000;
    private static long amountOfBreakTime = 30000;

    public static void setTaskTime(long taskTime) {
       amountOfTaskTime = taskTime;
    }

    public static long getTaskTime() {
        return amountOfTaskTime;
    }

    public static void setBreakTime(long breakTime) {
        amountOfBreakTime = breakTime;
    }

    public static long getBreakTime() {
        return amountOfBreakTime;
    }


}
