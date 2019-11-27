package com.example.simplecountdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * This activity lets the user edit or create a new timer with given parameters.
 */
public class SetTimer extends AppCompatActivity {
    public static final String EXTRA_TIME = "com.example.com.example.SimpleCountdownTimer.TIME";
    public static final String EXTRA_NAME = "com.example.com.example.SimpleCountdownTimer.NAME";
    private TextView edittext;
    private TextView edittext2;
    private TextView edittext3;
    private TextView edittext4;

    /**
     * Invoked when the activity starts.
     * @param savedInstanceState The default instanced state that is passed normally with this method.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Tag", "SetTimer called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        edittext = findViewById(R.id.editText);
        edittext2 = findViewById(R.id.editText2);
        edittext3 = findViewById(R.id.editText3);
        edittext4 = findViewById(R.id.editText4);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_TIME);

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
        SharedPreferences sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(MainActivity.EXTRA_TIME, totalTimeInMilliseconds);
        editor.putString(MainActivity.EXTRA_NAME, name2);
        editor.putLong("resetTime", totalTimeInMilliseconds);
        editor.apply();
        startActivity(intent);
    }
}
