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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Primary screen for displaying and selecting timers to add/edit/delete.
 */
public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    public static final String TAG = "MainActivity";
    public static final String EXTRA_TIME = "com.example.simplecountdowntimer.TIME";
    public static final String EXTRA_NAME = "com.example.simplecountdowntimer.NAME";
    public static final String EXTRA_RESET_TIME = "com.example.simplecountdowntimer.RESET_TIME";
    public static final String TIMER_DATA_KEY = "TIMER_DATA_KEY";

    //TODO: Convert to local
    /**
     * The array list of timers, can add or remove from this collection.
     */
    private ArrayList<Timer> _timers;

    /**
     * The adapter which converts the model timers to views.
     */
    private TimerListAdapter _adapter;

    private AudioEngine _audioEngine;

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


        _audioEngine = new AudioEngine(this);


        // ################
        // ### STEP ONE ### - Load the timers from data, if there are any.
        // ################
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        //NOTE: Uncomment the following to reset the timer data. Debug purposes only.
        /*SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();*/

        String jsonFileContents = sharedPref.getString(TIMER_DATA_KEY, "FAIL");

        if (jsonFileContents != "FAIL")
        {
            TimerSerializable[] timersLoaded = gson.fromJson(jsonFileContents, TimerSerializable[].class);
            Log.d(TAG, "Successfully loaded timers.");
            _timers = new ArrayList<>();

            for (int i = 0; i < timersLoaded.length; i++)
            {
                //Load each timer
                TimerSerializable serializable = timersLoaded[i];

                if (serializable.timerRunning) {
                    long timeEllapsedMilliseconds = System.currentTimeMillis() - serializable.lastTimeStampInMilliseconds;
                    serializable.timeLeftInMilliseconds -= timeEllapsedMilliseconds;
                }

                Timer timer = new Timer(timersLoaded[i].timeLeftInMilliseconds, timersLoaded[i].timerName, timersLoaded[i].resetTime, serializable.timerSound, serializable.timerColor);

                if (timersLoaded[i].timerRunning) {
                    timer.startTimer();
                    timer.setTimerRunning(timersLoaded[i].timerRunning);
                }

                _timers.add(timer);
            }
        }
        else
        {
            Log.d(TAG, "Failed to load timers, initializing new timer array.");
            //Failed to load, create empty
            _timers = new ArrayList<>();
        }


        // ################
        // ### STEP TWO ### - If a new or modified timer came from the set timer activity, we
        // ################   then add the new timer or modify the existing timer here.
        Intent intent = getIntent();
        long timeLeftInMillisecondsLoaded = intent.getLongExtra(EXTRA_TIME, -1);
        long resetTime = intent.getLongExtra(EXTRA_RESET_TIME, -1);
        String nameTimer = intent.getStringExtra(EXTRA_NAME);
        String timerSound = intent.getStringExtra(SetTimer.EXTRA_SOUND);
        int indexNewTimer = intent.getIntExtra(SetTimer.POSITION_INDEX, -1);
        int timerColor = intent.getIntExtra(SetTimer.EXTRA_COLOR, 0xFFFFFF);
        Log.d(TAG, "Index New Timer: " + indexNewTimer);

        if (indexNewTimer != -1)
        {
            //Determine if its a new timer or an existing timer by index. If the index is out of bounds, it is a new timer
            Log.d(TAG, "Size Timers: " + _timers.size());

            if (indexNewTimer < _timers.size()) {
                //It is an existing timer we are modifying
                _timers.set(indexNewTimer, new Timer(timeLeftInMillisecondsLoaded, nameTimer, resetTime, timerSound, timerColor));
            } else {
                //It is a new timer
                _timers.add(new Timer(timeLeftInMillisecondsLoaded, nameTimer, resetTime, timerSound, timerColor));
            }
        }


        // ##################
        // ### STEP THREE ### - We set up the view for each timer here
        // ##################
        ListView recyclerView = findViewById(R.id.timers_list_view);
        _adapter = new TimerListAdapter(this, R.layout.timer_adapter_view_layout, _timers);
        _adapter.MainActivityInterface = this;
        recyclerView.setAdapter(_adapter);


        // #################
        // ### STEP FOUR ### - Add an onClick listener to the add timer button.
        // #################
        FloatingActionButton addTimerButton = findViewById(R.id.add_timer_button);
        addTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimerButtonPress();
            }
        });

        /*SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int sound1 = soundPool.load(this, R.raw.digital_phone_ringing, 1);
        soundPool.play(sound1, 1, 1, 1, -1, 1);*/

        //AudioEngine.getInstance().AudioSetUp(this);
        //AudioEngine.getInstance().soundOne.start();
    }

    /**
     * Invoked when app stops, saves data to shared preferences
     */
    @Override
    public void onStop() {
        // When app stops, save data to shared preferences

        persistTimers();
        super.onStop();
    }

    /**
     * Saves timer data.
     */
    private void persistTimers() {
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        TimerSerializable[] timerSerializables = new TimerSerializable[_timers.size()];

        for (int i = 0; i < _timers.size(); i++) {
            timerSerializables[i] = new TimerSerializable(_timers.get(i));
        }

        Gson gson = new Gson();
        String jsonFile = gson.toJson(timerSerializables);

        Log.v(TAG, "JSON File: " + jsonFile);
        editor.putString(TIMER_DATA_KEY, jsonFile);
        editor.commit();
    }

    /**
     * Invoked when the add_timer_button is pressed.
     */
    public void addTimerButtonPress() {
        Log.d(TAG, "Timers Count: " + _timers.size());
        startSetTimerActivity(_timers.size());
    }

    /**
     * Invoked when a start_stop_button of one of the timers is pressed.
     * @param holder The view manager of the timer which the button was pressed on.
     */
    public void startStopButtonPress(TimerHolder holder) {
        Log.i(TAG, "Start Stop Button Pressed");
        Timer timer = holder.Timer;

        _audioEngine.StopSound(timer.getTimerSound());

        startStopTimer(timer, holder.StartStopButton, holder.SetResetButton);
    }

    /**
     * Invoked when a set_reset_button of one of the timers is pressed.
     * @param holder The view manager of the timer which the button was pressed on.
     */
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
            startSetTimerActivity(holder.Position);
        }
        else {
            Log.i("Tag", "ELSE ran");
            startSetTimerActivity(holder.Position);
        }
    }

    @Override
    public void playSound(String soundName) {
        _audioEngine.PlaySound(soundName);
    }

    @Override
    public void stopSound(String soundName) {
        _audioEngine.StopSound(soundName);
    }

    @Override
    public void deleteButtonPress(TimerHolder holder) {
        _timers.remove(holder.Position);
        _adapter.notifyDataSetChanged();
    }

    /**
     * Invoke to move to the set timer activity to create or modify a timer.
     * @param indexOfTimer The index of the timer to modify. If a new timer is desired make it the index of the new timer.
     */
    private void startSetTimerActivity(int indexOfTimer) {
        persistTimers();
        _audioEngine.Cleanup();
        Intent intent = new Intent(this, SetTimer.class);
        intent.putExtra(SetTimer.POSITION_INDEX, indexOfTimer);
        startActivity(intent);
    }

    /**
     * Either starts or pauses a timer
     * @param timer The timer to start or pause
     * @param startStopButton The start_stop_button of the timer.
     * @param setResetButton The set_reset_button of the timer.
     */
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
}
