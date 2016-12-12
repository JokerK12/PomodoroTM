package com.kk.pomodorotm;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kk.pomodorotm.adapters.TaskListItemWorkAdapter;
import com.kk.pomodorotm.date.Task;
import com.kk.pomodorotm.date.TaskDBHandler;
import com.kk.pomodorotm.date.Settings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WorkActivity extends AppCompatActivity {

    private Task task;
    private Task taskDone;
    private TaskCountDownTimer taskTimer;
    private TaskCountDownTimer breakTimer;
    private TaskListItemWorkAdapter adapter,adapterDoneTask;
    private TaskDBHandler dbHandler;
    private TextView taskName;
    private TextView taskTimerView;
    private ListView taskListView;
    private ListView taskDoneListView;
    private Button  buttonStart;
    private Button buttonTaskDone;

    private long amountOfTaskTime = Settings.getTaskTime();
    private long amountofBreakTime = Settings.getBreakTime();

    private String dialogTitle = "Czas na przerwe";
    private String dialogMessage = "Teraz zrób sobie 5 minut przerwy! :)";
    int taskCounter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        //Create reference to database
        dbHandler = new TaskDBHandler(this, null, null, 1);

        setupListViewAdapterTasktoDo();
        setAdapter();

        taskName = (TextView)findViewById(R.id.tv_task_name);
        taskTimerView = (TextView)findViewById(R.id.tv_task_timer);
        buttonStart = (Button)findViewById(R.id.btn_task_start_reset);
        buttonTaskDone = (Button)findViewById(R.id.btn_task_done);
        taskTimer = new TaskCountDownTimer(amountOfTaskTime+200,1000, this.taskTimerView);
        breakTimer = new TaskCountDownTimer(amountofBreakTime+200,1000, this.taskTimerView);


    }

    private void setupListViewAdapterTasktoDo() {
        adapter = new TaskListItemWorkAdapter(WorkActivity.this, R.layout.task_list_item_work, new ArrayList<Task>());
        taskListView = (ListView)findViewById(R.id.lv_task_work);
        taskListView.setAdapter(adapter);
        setupListViewAdapterTaskDone();
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("WorkActivity", " "+  adapter.getTaskName(position));
                task = adapter.getTask(position);
                setTaskCounterManagement(adapter.getTask(position));
            }
        });
    }

    private void setupListViewAdapterTaskDone() {
        adapterDoneTask = new TaskListItemWorkAdapter(WorkActivity.this, R.layout.task_list_item_work, new ArrayList<Task>());
        taskDoneListView = (ListView)findViewById(R.id.lv_task_work_done);
        taskDoneListView.setAdapter(adapterDoneTask);
//        taskDoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        //todo Wywalic ten kawalek  kodu
//                Log.d("WorkActivity", " "+  adapterDoneTask.getTaskName(position));
//                taskDone = adapterDoneTask.getTask(position);
//                setTaskCounterManagement(adapterDoneTask.getTask(position));
//            }
//        });
    }

    public void setTaskCounterManagement(Task task) {
        taskName.setText(task.getName());
        buttonStart.setClickable(true);
        buttonTaskDone.setClickable(true);

    }
    //Call method for button Task Done
    public void taskIsDone(View v) {
        taskTimer.cancel();
        breakTimer.cancel();
        task.setIstaskDone(true);
        readyToStartTask();
        dbHandler.updateTaskDone(task);
        Log.d("Database updated",dbHandler.databaseToString());
        setupListViewAdapterTasktoDo();
        setAdapter();
    }

    public void taskIntervalIncrease() {
        task.increaseTaskInterval();
        Log.d("Work intervals ",""+ task.getTaskInterval());
        dbHandler.updateTaskIntervals(task);
        Log.d("Database intervals upd",dbHandler.databaseToString());
    }

    boolean timerHasStarted = false;
    public void startTaskTimer(View v) {
        taskListView.setOnItemClickListener(null);
        if (!timerHasStarted) {
            taskTimer.start();
            timerHasStarted = true;
            taskIsWorking();
        }
        else {
            taskTimer.cancel();
            timerHasStarted = false;
            setupListViewAdapterTasktoDo();
            setAdapter();
            readyToStartTask();
        }
    }

    //Method call when ready to start task
    public void readyToStartTask() {
        buttonStart.setEnabled(true);

        int minutes = (int) (amountOfTaskTime / (60 * 1000));
        int seconds = (int) (amountOfTaskTime / 1000) % 60;

        taskTimerView.setText(String.format("%d:%02d",minutes,seconds));
        buttonStart.setText("START");
    }

    public void taskIsWorking() {
        buttonStart.setText("ANULUJ");
        buttonStart.setClickable(true);
        buttonStart.setEnabled(true);

    }



    //Adding tasks and taskDone to the adapter arrays
    public void setAdapter() {
        adapter.clear();
        adapterDoneTask.clear();
        ArrayList<Task> taskList = dbHandler.databaseGetTaskObject();
        //Add objects to adapter if date is the same as choosen day

        for(Task task : taskList) {
            Log.d("Work Activity Porównaj:", task.getDate() + "||" + (getCurrentDate()));
            if(task.getDate().equals(getCurrentDate()) && !task.getIsTaskDone()) {
                adapter.add(task);
            }
            else if (task.getDate().equals(getCurrentDate()) && task.getIsTaskDone())
            {
                adapterDoneTask.add(task);
            }
        }




    }


    //Returns String object  with current date
    private String getCurrentDate() {
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sqlDate);
        return currentDate;
    }

    //Fragment Dialog
    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(fm,"Przerwa");
    }

    //Timer
    public class TaskCountDownTimer extends CountDownTimer {
        TextView taskTextView;


        public TaskCountDownTimer(long timerLongTime, long timerInterval, TextView taskTextView) {
            super(timerLongTime, timerInterval);
            this.taskTextView = taskTextView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int minutes = (int) (millisUntilFinished / (60 * 1000));
            int seconds = (int) (millisUntilFinished / 1000) % 60;

            taskTextView.setText(String.format("%d:%02d",minutes,seconds));

        }

        @Override
        public void onFinish() {
            taskTextView.setText("00:00");
            showDialog();
            taskCounter = taskCounter +1;

        }

    }

    public class AlertDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    // Set Dialog Icon
                    .setIcon(R.drawable.abc_popup_background_mtrl_mult)
                    // Set Dialog Title
                    .setTitle(dialogTitle)
                    // Set Dialog Message
                    .setMessage(dialogMessage)

                    // Positive button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                           if (isTaskOrBreak()) {
                               taskStart(true);
                           }
                            else
                           {
                               taskBreak();
                               taskListView.setOnItemClickListener(null);
                           }
                        }
                    })

                    // Negative Button
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,	int which) {
                            // Do something else
                            setupListViewAdapterTasktoDo();
                            setAdapter();
                            if(isTaskOrBreak()) {
                                taskStart(false);
                                timerHasStarted = false;
                                readyToStartTask();

                            }
                            else
                            {
                                taskStart(false);
                                taskTimer.cancel();
                                taskCounter = 0;
                                taskIntervalIncrease();
                                setupListViewAdapterTasktoDo();
                                setAdapter();
                                timerHasStarted = false;
                                readyToStartTask();
                            }
                        }
                    }).create();
        }
    }

    public boolean isTaskOrBreak() {
        if(taskCounter % 2 == 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    private void taskBreak() {
        breakTimer.start();
        buttonStart.setText("Przerwa");
        buttonStart.setClickable(false);
        buttonStart.setEnabled(false);
        dialogTitle = "Czas na prace";
        dialogMessage = "Czas wrócić do pracy! :)";
        taskIntervalIncrease();
        setupListViewAdapterTasktoDo();
        setAdapter();

        //taskListView.setOnItemClickListener(null);

    }

    private void taskStart(boolean isTimerOn) {
        if(isTimerOn)
            taskTimer.start();
        dialogTitle = "Czas na przerwe";
        dialogMessage = "Teraz zrób sobie 5 minut przerwy! :)";
        taskIsWorking();
        if (isTimerOn)
            taskListView.setOnItemClickListener(null);
    }


}
