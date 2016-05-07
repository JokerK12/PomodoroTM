package com.kk.pomodorotm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kk.pomodorotm.adapters.TaskListItemScheduleAdapter;
import com.kk.pomodorotm.adapters.TaskListItemWorkAdapter;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;
import com.kk.pomodorotm.services.TaskCountDownTimer;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

public class WorkActivity extends AppCompatActivity {

    private TaskListItemWorkAdapter adapter;
    private TaskDBHandler dbHandler;
    private TextView taskName;
    private TextView taskTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        //Create reference to database
        dbHandler = new TaskDBHandler(this, null, null, 1);
        setupListViewAdapter();
        setAdapter();
        taskName = (TextView)findViewById(R.id.tv_task_name);
        taskTimer = (TextView)findViewById(R.id.tv_task_timer);


    }

    private void setupListViewAdapter() {
        adapter = new TaskListItemWorkAdapter(WorkActivity.this, R.layout.task_list_item_work, new ArrayList<Task>());
        ListView taskListView = (ListView)findViewById(R.id.lv_task_work);
        taskListView.setAdapter(adapter);


        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("WorkActivity", " "+  adapter.getTaskName(position));
                taskName.setText(adapter.getTaskName(position));

            }
        });
    }

    public void startTaskTimer(View v) {
        TaskCountDownTimer taskTimer = new TaskCountDownTimer(1500000,1000, this.taskTimer);
        taskTimer.start();
    }




    public void setAdapter() {
        adapter.clear();
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        //Add objects to adapter if date is the same as choosen day

        for(Task task : taskList) {
            Log.d("Work Activity Porównaj:", task.getDate() + "||" + (getCurrentDate()));
            if(task.getDate().equals(getCurrentDate())) {
                adapter.add(task);
            }
        }
    }

    //Returns String object  with current date
    private String getCurrentDate() {
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sqlDate);
        return currentDate;
    }
}
