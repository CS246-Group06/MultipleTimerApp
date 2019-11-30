package com.example.simplecountdowntimer;

/**
 * Timer data which is serialized. Can create a new timer from it, or save timer data with it to GSON.
 */
public class TimerSerializable {
    public long timeLeftInMilliseconds;
    public long lastTimeStampInMilliseconds;
    public long resetTime;
    public boolean timerRunning;
    public String timerName;

    /**
     * Initializes a serializable timer data object with each appropriate parameter
     * @param timeLeftInMilliseconds Time left in milliseconds
     * @param lastTimeStampInMilliseconds Current time in milliseconds.
     * @param resetTime Time to reset to.
     * @param timerRunning Whether the timer is running or not. Will catch up when loaded if "running".
     * @param timerName The name of the timer.
     */
    public TimerSerializable(long timeLeftInMilliseconds, long lastTimeStampInMilliseconds, long resetTime, boolean timerRunning, String timerName) {
        this.timeLeftInMilliseconds = timeLeftInMilliseconds;
        this.lastTimeStampInMilliseconds = lastTimeStampInMilliseconds;
        this.resetTime = resetTime;
        this.timerRunning = timerRunning;
        this.timerName = timerName;
    }

    /**
     * Initializes a serializable timer data object with a timer.
     * @param timer The timer to create serializable data with.
     */
    public TimerSerializable(Timer timer) {
        timeLeftInMilliseconds = timer.getTimerLeftInMilliseconds();
        lastTimeStampInMilliseconds = System.currentTimeMillis();
        resetTime = timer.getResetTime();
        timerRunning = timer.getTimerRunning();
        timerName = timer.getTimerName();
    }
}
