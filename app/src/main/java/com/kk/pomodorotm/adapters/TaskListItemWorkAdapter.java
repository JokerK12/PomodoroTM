package com.kk.pomodorotm.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.pomodorotm.R;
import com.kk.pomodorotm.date.Task;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Karol on 2016-04-26.
 */
public class TaskListItemWorkAdapter extends ArrayAdapter<Task> {

    private List<Task> items;
    private int layoutRescourceId;
    private Context context;



    public TaskListItemWorkAdapter(Context context, int layoutRescourceId, List<Task> items) {
        super(context, layoutRescourceId, items);
        this.layoutRescourceId = layoutRescourceId;
        this.context = context;
        this.items = items;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskItemHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutRescourceId, parent, false);

        holder = new TaskItemHolder();

        holder.task = items.get(position);
        holder.taskName = (TextView) row.findViewById(R.id.et_task_name);
        holder.taskDate = (TextView) row.findViewById(R.id.et_task_date);
        holder.taskIntervals = (TextView) row.findViewById(R.id.tv_intervals_quantity);
        holder.taskIntervalsImage = (ImageView) row.findViewById(R.id.iv_interval_clock);
        holder.taskDoneImage = (ImageView) row.findViewById(R.id.iv_task_done);

        row.setTag(holder);
        setupItem(holder);


        return row;
    }

    public void setupItem(TaskItemHolder holder) {

        holder.taskName.setText(holder.task.getName());
        holder.taskDate.setText(holder.task.getDateToShowInAdapter());
        setIntervalsGraphic(holder);
        setTaskDoneGraphic(holder);

    }

    public void setupItemTaskDone(TaskItemHolder holder) {
       // if (holder.task.getIsTaskDone()) {
            holder.taskName.setText(holder.task.getName());
            holder.taskDate.setText(holder.task.getDateToShowInAdapter());
            setIntervalsGraphic(holder);
            setTaskDoneGraphic(holder);
       // }
    }



   public String getTaskName(int position) {
       return items.get(position).getName();
   }

   public Task getTask(int position) {
       return items.get(position);
   }

    public static class TaskItemHolder {
        Task task;
        TextView taskName;
        TextView taskDate;
        TextView taskIntervals;
        ImageView taskIntervalsImage;
        ImageView taskDoneImage;
    }

    public void setIntervalsGraphic(TaskItemHolder holder) {
        if(holder.task.getTaskInterval() > 0) {
            holder.taskIntervalsImage.setVisibility(View.VISIBLE);
            holder.taskIntervals.setText(holder.task.getTaskInterval()+"x");
        }
    }

    public void setTaskDoneGraphic(TaskItemHolder holder) {
        Log.d("TaskName DoneGraphic ",""+ holder.task.getName()+ " " + holder.task.getIsTaskDone());
        if(holder.task.getIsTaskDone()) {
            holder.taskDoneImage.setVisibility(View.VISIBLE);

        }

    }



}
