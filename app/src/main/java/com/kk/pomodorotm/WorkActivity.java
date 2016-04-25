package com.kk.pomodorotm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.kk.pomodorotm.adapters.TaskListItemScheduleAdapter;
import com.kk.pomodorotm.adapters.TaskListItemWorkAdapter;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkActivity extends AppCompatActivity {

    private TaskListItemWorkAdapter adapter;
    TaskDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        //Create reference to database
        dbHandler = new TaskDBHandler(this, null, null, 1);
        setupListViewAdapter();
        setAdapter();

    }

    private void setupListViewAdapter() {
        adapter = new TaskListItemWorkAdapter(WorkActivity.this, R.layout.task_list_item_work, new ArrayList<Task>());
        ListView taskListView = (ListView)findViewById(R.id.lv_task_work);
        taskListView.setAdapter(adapter);
    }

    public void setAdapter() {
        adapter.clear();
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        //Add objects to adapter if date is the same as choosen day

        for(Task task : taskList) {
            Log.d("Work Activity Porównaj:", task.getDateAdapterAsString() + "||" + (getCurrentDate()));
            if(task.getDateAdapterAsString().equals(getCurrentDate())) {
                adapter.add(task);
            }
        }
    }

    //Returns String object  with current date
    private String getCurrentDate() {
        String currentDate = new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }
}
