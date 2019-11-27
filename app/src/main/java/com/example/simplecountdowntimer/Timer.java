package com.example.simplecountdowntimer;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Standard countdown timer. Set time to a certain amount and can count down to 0.
 */
public class Timer {
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private long resetTime;
    private boolean timerRunning = false;
    private String timerName;
    private MainActivityInterface MainActivityDelegate;

    /**
     * Constructor method.
     * @param delegate Activity Controller.
     * @param timeLeftInMilliseconds Current time.
     * @param name Name of timer.
     * @param resetTime The time to reset to, max time.
     */
    public Timer(MainActivityInterface delegate, long timeLeftInMilliseconds, String name, long resetTime){
        MainActivityDelegate = delegate;
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
        this.timerName = name;
        this.resetTime = resetTime;
    }

    public void setTimerRunning(boolean running){timerRunning = running;}

    /**
     * Resets the timer to the original time
     */
    public void reset(){timeLeftInMilliseconds = resetTime;}

    //Getters and Setters
    public boolean getTimerRunning(){return timerRunning;}
    public long getTimerLeftInMilliseconds(){return timeLeftInMilliseconds;}
    public String getTimerNamer(){return timerName;}
    public long getResetTime(){return resetTime;}


    /**
     * Starts the timer countdown from being paused.
     */
    public void startTimer() {
        Log.i("Tag", "Time left " + timeLeftInMilliseconds);
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override
            public void onTick(long l) {
                Log.i("Tag", "onTick called");
                timeLeftInMilliseconds = l;
                updateTimer(timeLeftInMilliseconds);
            }

            @Override
            public void onFinish() {
                Log.i("Tag", "onFinish called");

            }
        }.start();
        Log.i("Tag", "start called");
    }

    /**
     * Pauses the timer countdown from running.
     */
    public void stopTimer() {
        countDownTimer.cancel();
    }

    /**
     * Updates the appropriate controller with the new time (not needed to keep the timer running).
     * Simply updates the controller.
     * @param timeLeftInMilliseconds The time left in milliseconds.
     */
    public void updateTimer(long timeLeftInMilliseconds) {
        MainActivityDelegate.updateTimer(timeLeftInMilliseconds);
    }
}
