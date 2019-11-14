package com.example.simplecountdowntimer;

/**********************************************************************
*                                                                     *
*   From YouTube Video: https://www.youtube.com/watch?v=zmjfAcnosS0   *
*                                                                     *
***********************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    public static final String EXTRA_TIME = "com.example.simplecountdowntimer.TIME";
    public static final String EXTRA_NAME = "com.example.simplecountdowntimer.NAME";

    private TextView countDownText;
    private TextView setRest;
    private TextView editlabel;
    private Button countDownButton;
    private Button setReset;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Tag", "onCreate");
        editlabel = findViewById(R.id.labelName);
        setReset = findViewById(R.id.setReset);
        countDownText = findViewById(R.id.countDownText2);
        countDownButton = findViewById(R.id.countdown_button);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        // Set time with data from memory
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        long timeLeftInMillisecondsLoaded = sharedPref.getLong(MainActivity.EXTRA_TIME, 0);
        long resetTime= sharedPref.getLong("resetTime", 0);

        // Update local variable by pulling from shared preferences
        String timerName = sharedPref.getString(EXTRA_NAME, "Timer 1");
        editlabel.setText(timerName);

        // Updates variables with time and label set by user and passed with intent
        long timeLeftInMilliseconds = getIntent().getLongExtra(EXTRA_TIME, timeLeftInMillisecondsLoaded);
        timer = new Timer(this, timeLeftInMilliseconds, timerName, resetTime);



        // Updates the Text box displaying time set by user
        updateTimer(timeLeftInMilliseconds);
    }

    @Override
    public void onStop() {
        // When app stops, save data to shared preferences
        super.onStop();
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(MainActivity.EXTRA_TIME, timer.getTimerLeftInMilliseconds());
        editor.putString(MainActivity.EXTRA_NAME, timer.getTimerNamer());
        editor.putLong("resetTime", timer.getResetTime());
        editor.apply();

    }

    public void setTimer(View view) {
        Log.i("Tag", "setTimer called");
        // If there is time left on the timer, RESET it and continue to run.
        if(timer.getTimerRunning()) {
            Log.i("Tag", "IF ran");
            startStop();
            timer.reset();
            updateTimer(timer.getTimerLeftInMilliseconds());
            setReset.setText("SET");
        }
        // If there is time left on the timer, but timer has been paused, .
        else if(!timer.getTimerRunning() && (timer.getTimerLeftInMilliseconds() != timer.getResetTime())) {
            Log.i("Tag", "first ELSE IF ran");
            timer.getResetTime();
            timer.reset();
            updateTimer(timer.getTimerLeftInMilliseconds());
            setReset.setText("SET");
        }
        // If no time left on the timer, go to SetTimer activity
        else if(!timer.getTimerRunning() && timer.getTimerLeftInMilliseconds() == timer.getResetTime()){
            Log.i("Tag", "second ELSE IF ran");
            Intent intent = new Intent(this, SetTimer.class);
            startActivity(intent);
        }
        else {
            Log.i("Tag", "ELSE ran");
            Intent intent = new Intent(this, SetTimer.class);
            startActivity(intent);
        }
    }

    public void startStop () {
        if (timer.getTimerRunning()) {
            Log.i("Tag", "stopTimer called");
            timer.stopTimer();
            countDownButton.setText("START");
            timer.setTimerRunning(false);
        } else {
            Log.i("Tag", "startTimer called");
            timer.startTimer();
            countDownButton.setText("PAUSE");
            setReset.setText("RESET");
            timer.setTimerRunning(true);
        }
    }

    public void updateTimer(long timeLeftInMilliseconds) {
        int hours = (int) ((timeLeftInMilliseconds / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((timeLeftInMilliseconds / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60;
        String timeLeftText;

        timeLeftText = "" + hours;
        if(hours < 10) timeLeftText = "0" + hours;
        timeLeftText += ":";

        if(minutes < 10) timeLeftText += "0" + minutes;
        if(minutes >= 10) timeLeftText += minutes;
        timeLeftText += ":";

        if(seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
    }
}
