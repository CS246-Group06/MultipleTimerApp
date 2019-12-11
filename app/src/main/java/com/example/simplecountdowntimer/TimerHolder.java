package com.example.simplecountdowntimer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Manages a view for a single timer.
 */
public class TimerHolder implements TimerUiDelegate {


    //TODO: Change Timer to a delegate type for more security
    View view;
    Timer Timer;
    TextView NameTextView;
    TextView TimeLeftTextView;
    Button SetResetButton;
    Button StartStopButton;
    Button DeleteButton;
    MainActivityInterface MainActivityInterface;
    int Position;

    /**
     * Updates the user interface per second.
     * @param timeLeft
     */
    @Override
    public void updateUserInterface(String timeLeft) {
        TimeLeftTextView.setText(timeLeft);
        Log.d("AudioEngine", "Test");
    }

    /**
     * Invoked once when the timer runs out of time.
     */
    @Override
    public void timerAlarm() {
        Log.d("TimerHolder","Stopped timer");
        MainActivityInterface.playSound(Timer.getTimerSound());
    }
}