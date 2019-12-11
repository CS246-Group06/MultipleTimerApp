package com.example.simplecountdowntimer;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Standard countdown timer. Set time to a certain amount and can count down to 0.
 * TODO: Make serializable?
 */
public class Timer  {
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private long resetTime;
    private boolean timerRunning = false;
    private String timerName;
    public TimerUiDelegate TimerUiDelegate;
    private String timerSound;
    private int color;

    public String getTimerSound() {
        return timerSound;
    }

    public void setTimerSound(String timerSound) {
        this.timerSound = timerSound;
    }

    /**
     * Constructor method.
     * @param timeLeftInMilliseconds Current time.
     * @param name Name of timer.
     * @param resetTime The time to reset to, max time.
     */
    public Timer(long timeLeftInMilliseconds, String name, long resetTime, String timerSound, int color) {
        //TimerUiDelegate = delegate;
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
        this.timerName = name;
        this.resetTime = resetTime;
        this.timerSound = timerSound;
        this.color = color;
    }

    public void setTimerRunning(boolean running){timerRunning = running;}

    /**
     * Resets the timer to the original time
     */
    public void reset(){timeLeftInMilliseconds = resetTime;}

    //Getters and Setters
    public boolean getTimerRunning(){return timerRunning;}
    public long getTimerLeftInMilliseconds(){return timeLeftInMilliseconds;}
    public String getTimerName(){return timerName;}
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
                if (TimerUiDelegate != null) {
                    TimerUiDelegate.timerAlarm();
                }
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
        //MainActivityDelegate.updateTimer(timeLeftInMilliseconds);
        if (TimerUiDelegate != null) {
            TimerUiDelegate.updateUserInterface(GetStringTimeLeft());
        }
    }

    public String GetStringTimeLeft() {

        String returnValue;

        int hours = (int) ((timeLeftInMilliseconds / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((timeLeftInMilliseconds / (1000 * 60)) % 60);
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60;

        returnValue = "" + hours;
        if(hours < 10) returnValue = "0" + hours;
        returnValue += ":";

        if(minutes < 10) returnValue += "0" + minutes;
        if(minutes >= 10) returnValue += minutes;
        returnValue += ":";

        if(seconds < 10) returnValue += "0";
        returnValue += seconds;

        return returnValue;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    /*public void manageBlinkEffect(){
        ObjectAnimator anim = ObjectAnimator.ofArgb(view,"backgroundColor", Color.WHITE,Color.RED,Color.WHITE);
        anim.setDuration(800);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();

    }*/

}


