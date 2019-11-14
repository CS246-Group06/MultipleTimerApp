package com.example.simplecountdowntimer;

import android.os.CountDownTimer;
import android.util.Log;

public class Timer {
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private long resetTime;
    private boolean timerRunning = false;
    private String timerName;
    private MainActivityInterface MainActivityDelegate;

    public Timer(MainActivityInterface delegate, long timeLeftInMilliseconds, String name, long restTime){
        MainActivityDelegate = delegate;
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
        this.timerName = name;
        this.resetTime = restTime;
    }

    public void setTimerRunning(boolean running){timerRunning = running;}
    public void reset(){timeLeftInMilliseconds = resetTime;}
    public boolean getTimerRunning(){return timerRunning;}
    public long getTimerLeftInMilliseconds(){return timeLeftInMilliseconds;}
    public String getTimerNamer(){return timerName;}
    public long getResetTime(){return resetTime;}

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

    public void stopTimer() {
        countDownTimer.cancel();
    }

    public void updateTimer(long timeLeftInMilliseconds) {
        MainActivityDelegate.updateTimer(timeLeftInMilliseconds);
    }
}
