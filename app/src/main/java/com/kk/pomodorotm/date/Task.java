package com.kk.pomodorotm.date;

import java.util.Date;

/**
 * Created by Karol on 2016-03-18.
 */
public class Task {
    private int _id;
    private String _taskName;
    private Date   _taskDate;
    private boolean _taskDone;



    public Task(String taskName, Date taskDate) {
        this(taskName);
        this._taskDate = taskDate;
    }

    public Task(String taskName) {
        this._taskName = taskName;
    }

    public String getName() {
        return _taskName;
    }

    public void setName(String taskName) {
        this._taskName = taskName;
    }
}
