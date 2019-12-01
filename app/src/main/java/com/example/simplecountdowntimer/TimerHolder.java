package com.example.simplecountdowntimer;

import android.util.Log;
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
        Log.d("AudioEngine", "Test");
        if((Timer.getTimerLeftInMilliseconds()/ 1000) % 60 <= 0){ //(timeLeftInMilliseconds / 1000) % 60

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

            /*if(Timer.getTimerSound().equals("Digital Phone")){
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
            }*/
        }
    }
}