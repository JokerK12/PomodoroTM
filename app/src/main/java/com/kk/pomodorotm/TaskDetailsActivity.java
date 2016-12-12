package com.kk.pomodorotm;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.kk.pomodorotm.adapters.TaskListItemScheduleAdapter;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;

import java.sql.Date;
import java.util.ArrayList;



public class TaskDetailsActivity extends AppCompatActivity {

    private TaskListItemScheduleAdapter adapter;
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
    private Date getDateFromActivity () {

        Date date = null ;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = (Date) getIntent().getSerializableExtra("calendarDate");
        }


        return date;
    }

    private void setupListViewAdapter() {
        adapter = new TaskListItemScheduleAdapter(TaskDetailsActivity.this, R.layout.task_list_item_details, new ArrayList<Task>());
        ListView taskListView = (ListView)findViewById(R.id.lv_task);
        taskListView.setAdapter(adapter);
    }


    public void addTaskClickHandler(View v) {
        EditText getTaskName = (EditText)findViewById(R.id.et_task_namee);
        Task newTask= new Task(getTaskName.getText().toString());
        newTask.setDate(getDateFromActivity());
        Log.d("TaskDetailsActivity", getDateFromActivity().toString());
        dbHandler.addTask(newTask);
        printDatabase(); //logcat
        setAdapter();
    }

    public void removeTaskClickHandler(View v) {
        Task itemToRemove = (Task)v.getTag();
        dbHandler.deleteTask(itemToRemove.getName(), Date.valueOf(itemToRemove.getDate()));
        printDatabase(); //logcat
        setAdapter();
    }

    //Alert dialog with remove confirmation
    public void confirmRemoveTask(View v) {
        final View pastView = v;
        Task itemToRemove = (Task)v.getTag();
        new AlertDialog.Builder(TaskDetailsActivity.this)
                .setTitle(itemToRemove.getName())
                .setMessage("Napewno usunąć to zadanie?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        removeTaskClickHandler(pastView);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();
    }

    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        Log.d("DATABASE: ",dbString);
    }

    public void setAdapter() {
        adapter.clear();
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        Date temp = getDateFromActivity();
        Date tempYesterday = new Date(temp.getYear(),temp.getMonth(),temp.getDate()-1);
        //Add objects to adapter if date is the same as choosen day
        for(Task task : taskList) {
            Log.d("TaskDetailActi","Porównaj: "+task.getName() +" "+ (task.getDate()+" "+tempYesterday));                                                               //todo Wywalić ta czesc kodu
            if (task.getDate().equals(temp.toString())) {
                adapter.add(task);
            }
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
