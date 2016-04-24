package com.kk.pomodorotm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kk.pomodorotm.adapters.TaskListItemAdapter;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;


public class TaskDetailsActivity extends AppCompatActivity {
    private TaskListItemAdapter adapter;

    TextView date;
    TaskDBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);



        //Create reference to database
        dbHandler = new TaskDBHandler(this, null, null, 1);

        setupListViewAdapter();
        setAdapter();



    }

    //getDate return date from extras which pass date from ScheduleActivity
    private Date getDate () {
        int year = 0;
        int month = 0;
        int dayOfMonth = 0;

        Date date;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            year = extras.getInt("year");
            month = extras.getInt("month");
            dayOfMonth = extras.getInt("dayOfMonth");
        }
        date = new Date(year, month, dayOfMonth);
        Log.d("TaskDetailsActivity", String.valueOf(dayOfMonth + "/" + month + "/" + year));

        return date;
    }

    private void setupListViewAdapter() {
        adapter = new TaskListItemAdapter(TaskDetailsActivity.this, R.layout.task_list_item, new ArrayList<Task>());
        ListView taskListView = (ListView)findViewById(R.id.lv_task);
        taskListView.setAdapter(adapter);
    }


    public void addTaskClickHandler(View v) {
        EditText getTaskName = (EditText)findViewById(R.id.et_task_namee);
        Task newTask= new Task(getTaskName.getText().toString());
        newTask.setDate(getDate());
        dbHandler.addTask(newTask);
        printDatabase(); //logcat
        setAdapter();
    }

    public void removeTaskClickHandler(View v) {
        Task itemToRemove = (Task)v.getTag();
        dbHandler.deleteTask(itemToRemove.getName());
        printDatabase(); //logcat
        setAdapter();
    }

    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        Log.d("DATABASE: ",dbString);
    }

    public void setAdapter() {
        adapter.clear();
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        for(Task task : taskList) {
            adapter.add(task);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
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


}
