package com.kk.pomodorotm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kk.pomodorotm.date.Settings;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "I am in onCreate");
        loadSettings();
       // setUndoneTasks();

    }

    public void  setUndoneTasks() {
        TaskDBHandler dbHandler;
        dbHandler = new TaskDBHandler(this, null, null, 1);
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        Calendar c = Calendar.getInstance();
        Date temp = new Date(c.getTimeInMillis());
        Date tempYesterday = new Date(temp.getYear(),temp.getMonth(),temp.getDate()-1);

        for(Task task : taskList) {
            if(((task.getDate()).equals(tempYesterday.toString())) && !task.getIsTaskDone() ) {
                task.setDate(temp);
                dbHandler.updateTaskDate(task);
            }
        }
    }


    public void startWorkActivity(View v) {
        Intent workActivity = new Intent(getApplicationContext(), WorkActivity.class);
        startActivity(workActivity);
    }

    public void startScheduleActivity (View v) {
        Intent scheduleAcitivity = new Intent(getApplicationContext(),ScheduleActivity.class);
        startActivity(scheduleAcitivity);
    }

    public void  startSettingsActivity(View v) {
        Intent settingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settingsActivity);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadSettings() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Settings", 0);
        Settings.setTaskTime(settings.getLong("taskTime",0));
    }
}
