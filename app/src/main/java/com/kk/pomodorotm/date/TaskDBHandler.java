package com.kk.pomodorotm.date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Karol on 2016-04-18.
 * SQLite DB to save all of date
 */
public class TaskDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    public static final String  TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASKNAME = "taskname";
    public static final String COLUMN_TASKDATE = "taskdate";
    public static final String COLUMN_TASKDONE = "taskdone";
    public static final String COLUMN_TASKINTERVAL = "taskinterval";


    public TaskDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        //delete existing database
        //context.deleteDatabase(DATABASE_NAME);
        Log.d("DBHANDLER ", "jestem tutaj!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASKNAME + " TEXT, " +
                COLUMN_TASKDATE + " DATE, " +
                COLUMN_TASKDONE + " BOOLEAN, "+
                COLUMN_TASKINTERVAL + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addTask(Task task) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, task.getName());
        Log.d("Pokaz date", task.getDate());
        values.put(COLUMN_TASKDATE, task.getDate());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
        db.close();

    }


    //Delete a product from database
    public void deleteTask(String taskName, Date taskDate) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d("TaskDBHandler","DELETE FROM " + TABLE_TASKS + " WHERE (" + COLUMN_TASKNAME + "=\"" + taskName +"\"" + " AND " + COLUMN_TASKDATE + "=\"" + taskDate + "\");" );
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE (" + COLUMN_TASKNAME + "=\"" + taskName +"\"" + " AND " + COLUMN_TASKDATE + "=\"" + taskDate + "\");" );

    }

    //Update taskDone variable
    public void updateTaskDone(Task update) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TASKS + " SET " + COLUMN_TASKDONE + "=\"" + update.getIsTaskDone() + "\"" + " WHERE (" +
                COLUMN_TASKNAME + "=\"" + update.getName() +"\"" + " AND " + COLUMN_TASKDATE + "=\"" + update.getDate() + "\");" );

    }

    //Update taskIntervals variable
    public void updateTaskIntervals(Task update) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TASKS + " SET " + COLUMN_TASKINTERVAL + "=\"" + update.getTaskInterval() + "\"" + " WHERE (" +
                COLUMN_TASKNAME + "=\"" + update.getName() +"\"" + " AND " + COLUMN_TASKDATE + "=\"" + update.getDate() + "\");" );

    }

    //Get date from database and create ArrayList<Task>
    public ArrayList<Task> databaseGetTaskObject() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +TABLE_TASKS + " WHERE 1";
        //Cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in results
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("taskname"))!= null) {
                Task temp = new Task(c.getInt(c.getColumnIndex("_id")), c.getString((c.getColumnIndex("taskname"))));               //todo Dodac zmienne taskDone i taskIntervals
                temp.setDateString(c.getString((c.getColumnIndex("taskdate"))));
                temp.setIstaskDone(c.getInt((c.getColumnIndex("taskdone"))));
                temp.setTaskInterval(c.getInt(c.getColumnIndex("taskinterval")));

                Log.d("TaskDbHandler",c.getString((c.getColumnIndex("taskdate"))) );
                tasks.add(temp);
            }
            c.moveToNext();

        }

        c.close();
        db.close();
        return tasks;
    }

    //Print out database information as a string
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +TABLE_TASKS + " WHERE 1";
        //Cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in results
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex("taskname"))!= null) {
                dbString += c.getString(c.getColumnIndex("_id")) + " " + c.getString(c.getColumnIndex("taskname"));
                dbString += " " + c.getString(c.getColumnIndex("taskdate"));
                dbString += " " + c.getString(c.getColumnIndex("taskdone"));
                dbString += " " + c.getString(c.getColumnIndex("taskinterval"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }


}
