package com.kk.pomodorotm.services;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

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
//

    }

    @Override
    public void onFinish() {


    }
}