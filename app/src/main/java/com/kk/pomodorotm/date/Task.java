package com.kk.pomodorotm.date;

import android.text.format.DateFormat;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * Created by Karol on 2016-03-18.
 */
public class Task {
    private int _id;
    private String _taskName;
    private Date _taskDate;
    private boolean _taskDone = false;
    private int _taskInterval = 0;




    public Task(int id, String taskName) {
        this(taskName);
        this._id = id;
    }

    public Task(String taskName) {
        this._taskName = taskName;
    }

    public String getName() {
        return _taskName;
    }

    public int getId() {
        return _id;
    }

    public void setName(String taskName) {
        this._taskName = taskName;
    }

    public void setDate(Date date) {
        this._taskDate = date;
    }

    //Method set Date from Database string
    public void setDateString(String date) {
        this._taskDate = Date.valueOf(date);
        Log.d("Task setDateString ", "" +this._taskDate);


    }

    //Returns date
    public String getDate() {
        return this._taskDate.toString();
    }

    //Method returns date specifed to show in adapters
    public String getDateToShowInAdapter() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate;
        formattedDate = formatter.format(this._taskDate);

        return formattedDate;
    }


}