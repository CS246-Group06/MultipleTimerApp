package com.example.simplecountdowntimer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the view for all of the timers in the list.
 * Creates TimerHolders to manage each individual view
 */
public class TimerListAdapter extends ArrayAdapter<Timer> {
    public static final String TAG = "TimerListAdapter";

    private List<Timer> _objects;
    private Context _context;
    private int _resourceId;

    public MainActivityInterface MainActivityInterface;

    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public TimerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Timer> objects) {
        super(context, resource, objects);

        _resourceId = resource;
        _context = context;
        _objects = objects;
    }

    /**
     * Each timer gets a view when displaying all timers.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getTimerName();
        String timeLeft = getItem(position).GetStringTimeLeft();

        LayoutInflater inflater = LayoutInflater.from(_context);
        convertView = inflater.inflate(_resourceId, parent, false);

        TimerHolder holder = new TimerHolder();
        holder.view = convertView;
        holder.Timer = _objects.get(position);
        holder.SetResetButton = convertView.findViewById(R.id.set_reset_button);
        holder.StartStopButton = convertView.findViewById(R.id.start_stop_button);
        holder.NameTextView = (TextView) convertView.findViewById(R.id.timerName);
        holder.TimeLeftTextView = (TextView) convertView.findViewById(R.id.time_left_display);
        holder.Position = position;
        holder.MainActivityInterface = MainActivityInterface;
        Log.d("Holder:", "This is the position: " + holder.Position);

        //MUY IMPORTANTE
        convertView.setTag(holder);

        if (convertView.getTag() == null) {
            Log.d(TAG, "Holder is null");
        } else {
            Log.d(TAG, "Holder is not null");
        }

        SetUpView(holder);

        return convertView;
    }

    /**
     * Sets up the timer's view
     * @param holder The holder for the view.
     */
    private void SetUpView(final TimerHolder holder) {
        holder.NameTextView.setText(holder.Timer.getTimerName());

        //This can just be called more times when running!
        holder.TimeLeftTextView.setText(holder.Timer.GetStringTimeLeft());
        holder.Timer.TimerUiDelegate = holder;

        if (holder.Timer.getTimerRunning()) {
            holder.StartStopButton.setText("PAUSE");
            holder.SetResetButton.setText("RESET");
        }

        holder.StartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivityInterface.startStopButtonPress(holder);
            }
        });

        holder.SetResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityInterface.setResetButtonPress(holder);
            }
        });
    }

    /**
     * Holder for each view TODO: Refactor
     *//*
    public class TimerHolder implements TimerUiDelegate {
        //TODO: Change Timer to a delegate type for more security
        Timer Timer;
        TextView NameTextView;
        TextView TimeLeftTextView;
        Button SetResetButton;
        Button StartStopButton;

        @Override
        public void updateUserInterface(String timeLeft) {
            TimeLeftTextView.setText(timeLeft);
        }
    }*/
}
