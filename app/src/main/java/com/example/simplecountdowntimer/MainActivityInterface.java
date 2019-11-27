package com.example.simplecountdowntimer;

/**
 * The interface for the model and view to interact with the controller activity.
 */
public interface MainActivityInterface{

    /**
     * Updates the views to display the time, continually counting down
     * @param timeInMilliseconds The time left in milliseconds.
     */
    public void updateTimer(long timeInMilliseconds);
}
