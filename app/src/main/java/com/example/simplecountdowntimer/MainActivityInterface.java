package com.example.simplecountdowntimer;

import android.view.View;

public interface MainActivityInterface {
    void startStopButtonPress(TimerHolder holder);
    void setResetButtonPress(TimerHolder holder);
    void playSound(String soundName);
    void stopSound(String soundName);
    void deleteButtonPress(TimerHolder holder);
}
