package com.example.simplecountdowntimer;

import android.widget.Button;
import android.widget.TextView;

/**
 * Manages a view for a single timer.
 */
public class TimerHolder implements TimerUiDelegate {
    //TODO: Change Timer to a delegate type for more security
    Timer Timer;
    TextView NameTextView;
    TextView TimeLeftTextView;
    Button SetResetButton;
    Button StartStopButton;
    int Position;

    @Override
    public void updateUserInterface(String timeLeft) {
        TimeLeftTextView.setText(timeLeft);
    }
}
