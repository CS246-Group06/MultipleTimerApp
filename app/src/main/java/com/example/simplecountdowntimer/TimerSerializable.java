package com.example.simplecountdowntimer;

public class TimerSerializable {
    public long timeLeftInMilliseconds;
    public long resetTime;
    public boolean timerRunning;
    public String timerName;

    public TimerSerializable(long timeLeftInMilliseconds, long resetTime, boolean timerRunning, String timerName) {
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
        this.resetTime = resetTime;
        this.timerRunning = timerRunning;
        this.timerName = timerName;
    }

    public TimerSerializable(Timer timer) {
        timeLeftInMilliseconds = timer.getTimerLeftInMilliseconds();
        resetTime = timer.getResetTime();
        timerRunning = timer.getTimerRunning();
        timerName = timer.getTimerName();
    }
}
