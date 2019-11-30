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
import android.widget.ListView;

import com.google.gson.Gson;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Primary screen for displaying and selecting timers to add/edit/delete.
 */
public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    public static final String TAG = "MainActivity";

    public static final String EXTRA_TIME = "com.example.simplecountdowntimer.TIME";
    public static final String EXTRA_NAME = "com.example.simplecountdowntimer.NAME";
    public static final String EXTRA_RESET_TIME = "com.example.simplecountdowntimer.RESET_TIME";
    public static final String TIMER_DATA_KEY = "TIMER_DATA_KEY";

    //private TextView countDownText;
    //private TextView setRest;
    //private TextView editlabel;
    //private Button countDownButton;
    //private Button setReset;
    //private Timer timer;

    //TODO: Convert to local
    private ArrayList<Timer> _timers;
    private TimerListAdapter _adapter;

    /**
     * Invoked on creation of the activity. Sets up the timer display screen, or loads it from
     * memory.
     * @param savedInstanceState The default instanced state that is passed normally with this method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Tag", "onCreate");

        /*editlabel = findViewById(R.id.labelName);
        setReset = findViewById(R.id.setReset);
        countDownText = findViewById(R.id.countDownText2);
        countDownButton = findViewById(R.id.countdown_button);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });*/

        // Set time with data from memory
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonFileContents = sharedPref.getString(TIMER_DATA_KEY, "FAIL");

        if (jsonFileContents != "FAIL")
        {
            TimerSerializable[] timersLoaded = gson.fromJson(jsonFileContents, TimerSerializable[].class);


            _timers = new ArrayList<>();

            for (int i = 0; i < timersLoaded.length; i++)
            {

                Timer timer = new Timer(timersLoaded[i].timeLeftInMilliseconds, timersLoaded[i].timerName, timersLoaded[i].resetTime);

                if (timersLoaded[i].timerRunning) {
                    timer.startTimer();
                }

                _timers.add(timer);
            }
            //_timers.addAll(Arrays.asList(timersLoaded));
        }
        else
        {
            //Failed to load, create empty
            _timers = new ArrayList<>();

            _timers.add(new Timer(5000, "First Timer Test", 5000));
            _timers.add(new Timer(0, "Second Timer Test", 0));
            _timers.add(new Timer(0, "Third Timer Test", 0));
            _timers.add(new Timer(0, "Fourth Timer Test", 0));
            _timers.add(new Timer(0, "Wow this is working", 0));
            _timers.add(new Timer(0, "Still is", 0));
        }

        //long timeLeftInMillisecondsLoaded = sharedPref.getLong(MainActivity.EXTRA_TIME, 0);
        //long resetTime= sharedPref.getLong("resetTime", 0);

        // Update local variable by pulling from shared preferences
        //String timerName = sharedPref.getString(EXTRA_NAME, "Timer 1");
        //editlabel.setText(timerName);

        // Updates variables with time and label set by user and passed with intent
        //long timeLeftInMilliseconds = getIntent().getLongExtra(EXTRA_TIME, timeLeftInMillisecondsLoaded);
        //timer = new Timer(this, timeLeftInMilliseconds, timerName, resetTime);

        //Test to see if we are modifying a timer.
        Intent intent = getIntent();
        long timeLeftInMillisecondsLoaded = intent.getLongExtra(EXTRA_TIME, -1);
        long resetTime = intent.getLongExtra(EXTRA_RESET_TIME, -1);
        String nameTimer = intent.getStringExtra(EXTRA_NAME);
        int indexNewTimer = intent.getIntExtra(SetTimer.POSITION_INDEX, -1);

        if (indexNewTimer != -1)
        {
            _timers.set(indexNewTimer, new Timer(timeLeftInMillisecondsLoaded, nameTimer, resetTime));
        }

        // Updates the Text box displaying time set by user
        //updateTimer(timeLeftInMilliseconds);

        ListView recyclerView = findViewById(R.id.timers_list_view);
        _adapter = new TimerListAdapter(this, R.layout.timer_adapter_view_layout, _timers);
        _adapter.MainActivityInterface = this;
        recyclerView.setAdapter(_adapter);
    }



    /**
     * Invoked when app stops, save data to shared preferences
     */
    @Override
    public void onStop() {
        // When app stops, save data to shared preferences
        super.onStop();
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        TimerSerializable[] timerSerializables = new TimerSerializable[_timers.size()];

        for (int i = 0; i < _timers.size(); i++) {
            timerSerializables[i] = new TimerSerializable(_timers.get(i));
        }
        
        Gson gson = new Gson();
        String jsonFile = gson.toJson(timerSerializables);
        editor.putString(TIMER_DATA_KEY, jsonFile);
        editor.commit();

        /*editor.putLong(MainActivity.EXTRA_TIME, timer.getTimerLeftInMilliseconds());
        editor.putString(MainActivity.EXTRA_NAME, timer.getTimerName());
        editor.putLong("resetTime", timer.getResetTime());
        editor.apply();*/

    }

    public void startStopButtonPress(TimerHolder holder) {
        Log.i(TAG, "Start Stop Button Pressed");
        Timer timer = holder.Timer;

        startStopTimer(timer, holder.StartStopButton, holder.SetResetButton);
    }

    public void setResetButtonPress(TimerHolder holder) {

        if (holder == null) {
            Log.d(TAG, "Holder is null");
        } else {
            Log.d(TAG, "Holder is not null");
        }

        Timer timer = holder.Timer;

        Log.i("Tag", "setTimer called");
        // If there is time left on the timer, RESET it and continue to run.
        if(timer.getTimerRunning()) {
            Log.i("Tag", "IF ran");
            startStopTimer(timer, holder.StartStopButton, holder.SetResetButton);
            timer.reset();
            holder.TimeLeftTextView.setText(timer.GetStringTimeLeft());
            holder.SetResetButton.setText("SET");
        }
        // If there is time left on the timer, but timer has been paused, .
        else if(!timer.getTimerRunning() && (timer.getTimerLeftInMilliseconds() != timer.getResetTime())) {
            Log.i("Tag", "first ELSE IF ran");
            timer.getResetTime();
            timer.reset();
            holder.TimeLeftTextView.setText(timer.GetStringTimeLeft());
            holder.SetResetButton.setText("SET");
        }
        // If no time left on the timer, go to SetTimer activity
        else if(!timer.getTimerRunning() && timer.getTimerLeftInMilliseconds() == timer.getResetTime()){
            Log.i("Tag", "second ELSE IF ran");
            Intent intent = new Intent(this, SetTimer.class);
            intent.putExtra(SetTimer.POSITION_INDEX, holder.Position);
            startActivity(intent);
        }
        else {
            Log.i("Tag", "ELSE ran");
            Intent intent = new Intent(this, SetTimer.class);
            Log.d(TAG, "Holder Position in Intent: " + holder.Position);
            intent.putExtra(SetTimer.POSITION_INDEX, holder.Position);
            startActivity(intent);
        }
    }

    private void startStopTimer(Timer timer, Button startStopButton, Button setResetButton) {

        if (timer.getTimerRunning()) {
            Log.i("Tag", "stopTimer called");
            timer.stopTimer();
            startStopButton.setText("START");
            timer.setTimerRunning(false);
        } else {
            Log.i("Tag", "startTimer called");
            timer.startTimer();
            startStopButton.setText("PAUSE");
            setResetButton.setText("RESET");
            timer.setTimerRunning(true);
        }
    }


    /**
     * Called when we set a new or existing timer.
     */
    /*public void setTimer() {
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
    }*/

    /**
     * Invoked on pressing either the start or stop button.
     */
    /*public void startStop () {
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
    }*/

    /**
     * Updates the views to display the time, continually counting down
     * @param timeLeftInMilliseconds The time left in milliseconds.
     */
    /*public void updateTimer(long timeLeftInMilliseconds) {
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
    }*/
}
