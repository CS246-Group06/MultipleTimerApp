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
        if(Timer.getTimerLeftInMilliseconds() <= 0){
            if(Timer.getTimerSound().equals("Digital Phone")){
                AudioEngine.getInstance().soundPool.play(AudioEngine.getInstance().sound1, 1, 1, 0, -1, 1);
            }
            else if(Timer.getTimerSound().equals("Gentle Wake")){
                AudioEngine.getInstance().soundPool.play(AudioEngine.getInstance().sound2, 1, 1, 0, -1, 1);
            }
            else if(Timer.getTimerSound().equals("Grandfather Clock")){
                AudioEngine.getInstance().soundPool.play(AudioEngine.getInstance().sound3, 1, 1, 0, -1, 1);
            }
            else if(Timer.getTimerSound().equals("Bell Alarm Clock")){
                AudioEngine.getInstance().soundPool.play(AudioEngine.getInstance().sound4, 1, 1, 0, -1, 1);
            }
            else{
                AudioEngine.getInstance().soundPool.play(AudioEngine.getInstance().sound5, 1, 1, 0, -1, 1);
            }
        }
    }
}
