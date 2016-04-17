package com.kk.pomodorotm.date;

import java.util.Date;

/**
 * Created by Karol on 2016-03-18.
 */
public class Task {
    private String taskName;
    private Date   taskDate;
    private boolean taskDone;


    public Task(String taskName, Date taskDate) {
        this.taskName = taskName;
        this.taskDate = taskDate;
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public String getName() {
        return taskName;
    }
}
