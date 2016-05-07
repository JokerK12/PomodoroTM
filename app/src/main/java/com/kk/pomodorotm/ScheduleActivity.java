package com.kk.pomodorotm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.sql.Date;

public class ScheduleActivity extends AppCompatActivity {

    CalendarView schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


    schedule = (CalendarView) findViewById(R.id.schedule);

        schedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView view,  int year, int month, int dayOfMonth) {
                month = month+1;
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                final Date calendarDate = Date.valueOf(year+"-"+month+"-"+dayOfMonth);

                findViewById(R.id.btn_create_task).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent TaskDetailsActivity = new Intent(getApplicationContext(), TaskDetailsActivity.class);
                        TaskDetailsActivity.putExtra("calendarDate",calendarDate);

                        startActivity(TaskDetailsActivity);
                    }
                });


            }
        });



    }
}
