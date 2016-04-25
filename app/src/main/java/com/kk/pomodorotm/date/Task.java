package com.kk.pomodorotm.date;

import android.util.Log;

import java.util.Date;

/**
 * Created by Karol on 2016-03-18.
 */
public class Task {
    private int _id;
    private String _taskName;
    private Date   _taskDate;
    private boolean _taskDone;



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
        String[] dateInt = date.split("/");
        Log.d("Parse Date: ", (Integer.parseInt(dateInt[0])+ " "+Integer.parseInt(dateInt[1])+ " "+ Integer.parseInt(dateInt[2])));

        this._taskDate = new Date(Integer.parseInt(dateInt[2]),Integer.parseInt(dateInt[1]),Integer.parseInt(dateInt[0]));

    }

    public String getDate() {
        return this._taskDate.getDate()+ "/" + this._taskDate.getMonth() + "/" + this._taskDate.getYear();
    }

    //Moths are order from 0 to 11 method returns correct month for adapter month+1
    public String getDateAdapterAsString() {
        return this._taskDate.getDate()+ "/" + (this._taskDate.getMonth()+1) + "/" + this._taskDate.getYear();
    }

    public static String getDateAsString(Date date) {
        return date.getDate()+ "/" + date.getMonth() + "/" + date.getYear();
    }


}