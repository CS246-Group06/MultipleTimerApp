package com.example.simplecountdowntimer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

public class AudioEngine {
    public SoundPool soundPool;
    public int sound1, sound2, sound3, sound4, sound5;
    //public HashMap<String, MediaPlayer> sounds;
    public HashMap<String, Integer> SoundIndices;

    public AudioEngine(Context context){
        // Setup sounds to play when timer reaches zero
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound1 = soundPool.load(context, R.raw.digital_phone_ringing, 1);
        sound2 = soundPool.load(context, R.raw.gentle_wake_alarm_clock, 1);
        sound3 = soundPool.load(context, R.raw.grandfather_clock_chimes, 1);
        sound4 = soundPool.load(context, R.raw.twin_bell_alarm_clock_sound, 1);
        sound5 = soundPool.load(context, R.raw.wecker_sound, 1);
        Log.i("Tag", "Sound files variables are set");

        SoundIndices = new HashMap<>();
        SoundIndices.put("sound1", sound1);
        SoundIndices.put("sound2", sound2);
        SoundIndices.put("sound3", sound3);
        SoundIndices.put("sound4", sound4);
        SoundIndices.put("sound5", sound5);

        /*sounds = new HashMap<>();

        sounds.put("sound1", MediaPlayer.create(context, R.raw.digital_phone_ringing));
        sounds.put("sound2", MediaPlayer.create(context, R.raw.gentle_wake_alarm_clock));
        sounds.put("sound3", MediaPlayer.create(context, R.raw.grandfather_clock_chimes));
        sounds.put("sound4", MediaPlayer.create(context, R.raw.twin_bell_alarm_clock_sound));
        sounds.put("sound5", MediaPlayer.create(context, R.raw.wecker_sound));

        for (MediaPlayer player:
                sounds.values()) {
            try {
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void Cleanup()
    {
        soundPool.release();
        soundPool = null;
        SoundIndices = null;

        /*for (MediaPlayer player:
             sounds.values()) {
            if (player.isPlaying()) {
                player.stop();
            }

            player.release();
        }*/
    }


    public void PlaySound(String key)
    {
        soundPool.play(SoundIndices.get(key), 1, 1, 1, 0, 1f);

        /*if (sounds.containsKey(key)) {
            sounds.get(key).reset();
            sounds.get(key).start();
        }
        else {
            Log.d("AUDIO PLAYER","WRONG KEY PASSED FOR SOME REASON: " + key);
        }*/
    }

    public void StopSound(String key)
    {
        soundPool.stop(SoundIndices.get(key));
        /*if (sounds.containsKey(key)) {
            sounds.get(key).reset();
        }
        else {
            Log.d("AUDIO PLAYER","WRONG KEY PASSED FOR SOME REASON: " + key);
        }*/
    }

}
