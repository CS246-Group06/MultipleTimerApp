package com.example.simplecountdowntimer;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class AudioEngine {
    public SoundPool soundPool;
    public int sound1, sound2, sound3, sound4, sound5;

    private static final AudioEngine Instance = new AudioEngine();
    private AudioEngine(){}
    public static AudioEngine getInstance(){
        return Instance;
    }

    public void AudioSetUp(Context context){
        // Setup sounds to play when timer reaches zero
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound1 = soundPool.load(context, R.raw.digital_phone_ringing, 1);
        sound2 = soundPool.load(context, R.raw.gentle_wake_alarm_clock, 1);
        sound3 = soundPool.load(context, R.raw.grandfather_clock_chimes, 1);
        sound4 = soundPool.load(context, R.raw.twin_bell_alarm_clock_sound, 1);
        sound5 = soundPool.load(context, R.raw.wecker_sound, 1);
        Log.i("Tag", "Sound files variables are set");
    }
}
