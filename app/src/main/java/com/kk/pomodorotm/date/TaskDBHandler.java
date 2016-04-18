package com.kk.pomodorotm.date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public TaskDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASKNAME + " TEXT" +
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
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
        db.close();

    }

    //Delete a product from database
    public void deleteTask(String taskName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASKNAME + "=\"" + taskName + "\";");

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
                tasks.add(new Task(c.getString((c.getColumnIndex("taskname")))));
            }

        }


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
                dbString += c.getString(c.getColumnIndex("taskname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }


}