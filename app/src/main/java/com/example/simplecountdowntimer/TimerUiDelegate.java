package com.example.simplecountdowntimer;

/**
 * The interface for the model and view to interact with the controller activity.
 */
public interface TimerUiDelegate {

    //public void updateTimer(long timeInMilliseconds);

    /**
     *
     * @param timeLeft
     */
    void updateUserInterface(String timeLeft);
}
