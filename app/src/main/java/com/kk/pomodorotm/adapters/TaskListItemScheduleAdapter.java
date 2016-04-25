package com.kk.pomodorotm.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kk.pomodorotm.R;
import com.kk.pomodorotm.date.Task;

import java.util.List;

/**
 * Created by Karol on 2016-04-14.
 */

public class TaskListItemScheduleAdapter extends ArrayAdapter<Task> {

    private List<Task> items;
    private int layoutRescourceId;
    private Context context;

    public TaskListItemScheduleAdapter(Context context, int layoutRescourceId, List<Task> items) {
        super(context, layoutRescourceId, items);
        this.layoutRescourceId = layoutRescourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskItemHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutRescourceId, parent, false);

        holder = new TaskItemHolder();


        holder.task = items.get(position);
        holder.removeTaskButton = (ImageButton)row.findViewById(R.id.ib_task_remove_button);
        holder.removeTaskButton.setTag(holder.task);
        holder.taskName = (TextView)row.findViewById(R.id.et_task_name);
        holder.taskDate = (TextView)row.findViewById(R.id.et_task_date);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }


    public void setupItem(TaskItemHolder holder) {
        holder.taskName.setText(holder.task.getName());
        Log.d("Adapter dostal: ", holder.task.getDateAdapterAsString());
        holder.taskDate.setText(holder.task.getDateAdapterAsString());
    }

    public static class TaskItemHolder {
        Task task;
        TextView taskName;
        TextView taskDate;
        ImageButton removeTaskButton;
    }
}
