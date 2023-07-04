package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import com.example.myapplication.databinding.Fragment3Binding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BlankFragment3 extends Fragment {

    private Fragment3Binding binding; // 추가




    boolean changed_1=false,changed_2=false;

    private long startTime = 0L;
    private long elapsedTime = 0L;

    private long lastWeek;

    long pauseOffset=0;


    private Handler timerHandler = new Handler();
    private Runnable updateTimerThread;
    private boolean wasTuesday;

    int week;

    private static final long TIME_THRESHOLD = 2 * 1000; // 2초(밀리초 단위)
    private static final long TIME_THRESHOLD_2 = 4 * 1000; // 4초(밀리초 단위)
    private static final int DEFAULT_IMAGE = R.drawable.character_start;
    private static final int NEW_IMAGE = R.drawable.character_1;
    private static final int NEW_IMAGE_2 = R.drawable.character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = Fragment3Binding.inflate(inflater, container, false);




        // Find the TextView
        TextView dateTextView = binding.dateTextView;

        // Get the current date
        Calendar currentCalendar = Calendar.getInstance();

        // Get the start date (2023-06-29)
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, 5, 29); // Month is 0-based, so 5 is June

        // Calculate the week
        week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;



        // Format the date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = formatter.format(currentCalendar.getTime());


        // Set the current date and week to the TextView
        dateTextView.setText(week+"주차"+"    "+formattedDate);

        binding.imageView.setImageResource(DEFAULT_IMAGE);

        // Start button
        // Start button
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startButton.getText().toString().equalsIgnoreCase("start")) {
                    startTime = SystemClock.elapsedRealtime();
                    binding.startButton.setText("Stop"); // Change button text to "Stop"
                    binding.chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    binding.chronometer.start();

                    // Timer Thread
                    updateTimerThread = new Runnable() {
                        public void run() {
                            elapsedTime = SystemClock.elapsedRealtime() - startTime;
                            if (elapsedTime >= TIME_THRESHOLD_2 && !changed_1) {
                                binding.imageView.setImageResource(NEW_IMAGE_2);
                                changed_1=true;
                            } else if (elapsedTime >= TIME_THRESHOLD && !changed_2 ) {
                                binding.imageView.setImageResource(NEW_IMAGE);
                                changed_2=true;
                            }
                            timerHandler.postDelayed(this, 1000); // Check every second
                        }
                    };
                    timerHandler.postDelayed(updateTimerThread, 1000); // Delay
                } else {
                    pauseOffset = SystemClock.elapsedRealtime() - binding.chronometer.getBase();

                    // Stop the chronometer
                    binding.chronometer.stop();

                    // Stop the timer
                    timerHandler.removeCallbacks(updateTimerThread);

                    binding.startButton.setText("Start"); // Change button text to "Start"
                }
            }
        });








        binding.buttonA.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if(week==1)
                {
                    Intent intent = new Intent(getActivity(), MadHouse.class);
                    startActivity(intent);
                }
//                else if (week==2) {
//                    Intent intent = new Intent(getActivity(), MadHouse_2.class);
//                    startActivity(intent);
//                }
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTime", startTime);
        editor.putLong("pauseOffset", pauseOffset);
        editor.putBoolean("changed_1", changed_1);
        editor.putBoolean("changed_2", changed_2);
        editor.putLong("elapsedTime", elapsedTime);

        editor.apply();
    }



}