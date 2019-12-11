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
        /*if((Timer.getTimerLeftInMilliseconds()/ 1000) % 60 <= 0){ //(timeLeftInMilliseconds / 1000) % 60

            if(Timer.getTimerSound().equals("Digital Phone")){
                Log.d("AudioEngine", "Sound One Played");
                AudioEngine.getInstance().soundOne.start();
            }
            else if(Timer.getTimerSound().equals("Gentle Wake")){
                Log.d("AudioEngine", "Sound Two Played");
                AudioEngine.getInstance().soundTwo.start();
            }
            else if(Timer.getTimerSound().equals("Grandfather Clock")){
                Log.d("AudioEngine", "Sound Three Played");
                AudioEngine.getInstance().soundThree.start();
            }
            else if(Timer.getTimerSound().equals("Bell Alarm Clock")){
                Log.d("AudioEngine", "Sound Four Played");
                AudioEngine.getInstance().soundFour.start();
            }
            else{
                Log.d("AudioEngine", "Sound Five Played");
                AudioEngine.getInstance().soundFive.start();
            }
        }*/
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