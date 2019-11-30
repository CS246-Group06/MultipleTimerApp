package com.example.simplecountdowntimer;

/**
 * The interface for the model and view to interact with the controller activity.
 */
public interface TimerUiDelegate {

    /**
     * Updates the user interface with the current time.
     * @param timeLeft
     */
    void updateUserInterface(String timeLeft);
}
