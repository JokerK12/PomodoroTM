package com.kk.pomodorotm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kk.pomodorotm.date.Settings;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seekBarIntervalTime;
    private TextView intervalLength;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeSeekBars();

        intervalLength.setText("Długość interwału: " + ((int)(Settings.getTaskTime()/(60 * 1000))) +"minut");


        seekBarIntervalTime.setProgress((int)(Settings.getTaskTime()/(60 * 1000)));
        seekBarIntervalTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float progress = 20;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                //Toast.makeText(getApplicationContext(), "Dlugośc interwalu: " + progress, Toast.LENGTH_SHORT ).show();
                progress = (120*(progress/100));
                Log.d("Aktualny status timera " , ""+(int)progress);
                intervalLength.setText("Długość interwału: "+(int)progress +"minut");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.setTaskTime((long)progress*60000);
                Log.d("Timer ustawiony", " TRUE TRUE TRUE");
                saveSettings();
            }
        });
    }

    public void saveSettings() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("taskTime",Settings.getTaskTime());
        editor.apply();
    }

    private void initializeSeekBars() {
        seekBarIntervalTime = (SeekBar)findViewById(R.id.seekBarIntervalTime);
        intervalLength = (TextView)findViewById(R.id.tv_intervalLenght);
    }

}
