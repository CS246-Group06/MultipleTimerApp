package com.example.simplecountdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This activity lets the user edit or create a new timer with given parameters.
 */
public class SetTimer extends AppCompatActivity {
    public static final String TAG = "SetTimer";
    public static final String POSITION_INDEX = "com.example.com.example.SimpleCountdownTimer.INDEX";
    public static final String EXTRA_TIME = "com.example.com.example.SimpleCountdownTimer.TIME";
    public static final String EXTRA_NAME = "com.example.com.example.SimpleCountdownTimer.NAME";
    public static final String EXTRA_SOUND = "com.example.com.example.SimpleCountdownTimer.SOUND";
    private TextView edittext;
    private TextView edittext2;
    private TextView edittext3;
    private TextView edittext4;
    private String sound;

    /**
     * Indicates which timer we are modifying.
     */
    private int _indexOfTimer;

    /**
     * Invoked when the activity starts.
     * @param savedInstanceState The default instanced state that is passed normally with this method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Tag", "SetTimer called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        // Setup spinner
        final Spinner spinner;
        ArrayAdapter<CharSequence> adapter;
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.sound_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sound = spinner.getItemAtPosition(position).toString();
                Log.i("Tag", "Sound set to " + sound);
                if(sound.equals("Digital Phone")){
                    sound = "sound1";
                    Log.i("Tag", "Sound1 set to " + sound);
                }
                else if(sound.equals("Gentle Wake")){
                    sound = "sound2";
                    Log.i("Tag", "Sound2 set to " + sound);
                }
                else if(sound.equals("Grandfather Clock")){
                    sound = "sound3";
                    Log.i("Tag", "Sound3 set to " + sound);
                }
                else if(sound.equals("Bell Alarm Clock")){
                    sound = "sound4";
                    Log.i("Tag", "Sound4 set to " + sound);
                }
                else{
                    sound = "sound5";
                    Log.i("Tag", "Sound5 set to " + sound);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edittext = findViewById(R.id.editText);
        edittext2 = findViewById(R.id.editText2);
        edittext3 = findViewById(R.id.editText3);
        edittext4 = findViewById(R.id.editText4);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        _indexOfTimer = intent.getIntExtra(POSITION_INDEX, -1);

        Log.d(TAG, "Index of Timer is: " + _indexOfTimer);

        edittext.setText("Timer 1");
        edittext2.setText("00");
        edittext3.setText("00");
        edittext4.setText("00");
        Log.i("Tag", "Finished");
    }

    /**
     * Finalizes the user input and creates/applies changes to the timer.
     * @param view The view which will be used for the timer.
     */
    public void loadTime (View view) {
        Log.i("Tag", "loadTime called");

        // Get the values put in by the user and convert to Strings
        TextView name1 = findViewById(R.id.editText);
        TextView minutes1 = findViewById(R.id.editText2);
        TextView seconds1 = findViewById(R.id.editText3);
        TextView hours1 = findViewById(R.id.editText4);
        String minutes2 = minutes1.getText().toString();
        String seconds2 = seconds1.getText().toString();
        String hours2 = hours1.getText().toString();
        String name2 = name1.getText().toString();

        // Do the math
        long minutesInMilliseconds = 60000 * Integer.parseInt(minutes2);
        long secondsInMilliseconds = 1000 * Integer.parseInt(seconds2);
        long hoursInMilliseconds = 3600000 * Integer.parseInt(hours2);
        long totalTimeInMilliseconds = minutesInMilliseconds + secondsInMilliseconds + hoursInMilliseconds;

        // Save time and label data to shared preference and go back to main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(POSITION_INDEX, _indexOfTimer);
        intent.putExtra(MainActivity.EXTRA_NAME, name2);
        intent.putExtra(MainActivity.EXTRA_TIME, totalTimeInMilliseconds);
        intent.putExtra(MainActivity.EXTRA_RESET_TIME, totalTimeInMilliseconds);
        intent.putExtra(MainActivity.EXTRA_SOUND, sound);
        startActivity(intent);
    }
}
