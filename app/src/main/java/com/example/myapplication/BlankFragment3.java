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
    boolean changed_1=false,changed_2=false;

    private long startTime = 0L;
    private long elapsedTime = 0L;

    private long lastWeek;
    long time_passed;
    private Handler timerHandler = new Handler();
    private Runnable updateTimerThread;
    private boolean wasTuesday;

    int week;

    private static final long TIME_THRESHOLD = 5 * 1000; // 2초(밀리초 단위)
    private static final long TIME_THRESHOLD_2 = 10 * 1000; // 4초(밀리초 단위)
    private static final int DEFAULT_IMAGE = R.drawable.character_start;
    private static final int NEW_IMAGE = R.drawable.character_1;
    private static final int NEW_IMAGE_2 = R.drawable.character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Fragment3Binding binding = Fragment3Binding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences_1 = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        wasTuesday = sharedPreferences_1.getBoolean("wasTuesday", false);

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
// Get the current day of the week
        int dayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK);
        // Check if it's Thursday and it wasn't Thursday the last time we checked
        if (dayOfWeek == Calendar.TUESDAY && !wasTuesday) {
            // It's Thursday and it wasn't Thursday the last time, reset the timer and save the new state
            elapsedTime = 0;
            startTime = SystemClock.elapsedRealtime();
            wasTuesday = true;
        } else if (dayOfWeek != Calendar.TUESDAY) {
            // It's not Tuesday, update the state
            wasTuesday = false;
        }

        // Save the state in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences_1.edit();
        editor.putBoolean("wasTuesday", wasTuesday);
        editor.apply();



        //... existing code ...

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myWeek", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_1 = sharedPreferences.edit();
        editor_1.putLong("week", week);
        editor_1.apply();

        // Format the date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = formatter.format(currentCalendar.getTime());


        // Set the current date and week to the TextView
        dateTextView.setText(week+"주차"+"    "+formattedDate);

        binding.imageView.setImageResource(DEFAULT_IMAGE);

        // Start button
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_passed=SystemClock.elapsedRealtime() + elapsedTime;
                binding.chronometer.setBase(time_passed);
                binding.chronometer.start();
                binding.startButton.setEnabled(false);
                binding.stopButton.setEnabled(true);
                startTime = SystemClock.elapsedRealtime();

                //데이터 공유하기


                // Timer Thread
                updateTimerThread = new Runnable() {
                    public void run() {
                        elapsedTime = SystemClock.elapsedRealtime() - startTime;
                        if (elapsedTime >= TIME_THRESHOLD_2 && changed_1 == false) {
                            binding.imageView.setImageResource(NEW_IMAGE_2);
                            changed_1=true;
                        } else if (elapsedTime >= TIME_THRESHOLD && changed_2 == false) {
                            binding.imageView.setImageResource(NEW_IMAGE);
                            changed_2=true;
                        }
                        timerHandler.postDelayed(this, 1000); // Check every second
                    }
                };
                timerHandler.postDelayed(updateTimerThread, 1000); // Delay
            }
        });

        // Stop button hohotoh
        // Stop button
        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the elapsed time
                elapsedTime = time_passed-SystemClock.elapsedRealtime();

                // Save the elapsed time in SharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("elapsedTime", elapsedTime);
                editor.putLong("lastElapsedTime", elapsedTime); // Save the last elapsed time
                editor.apply();

                // Stop the chronometer
                binding.chronometer.stop();

                // Update button states
                binding.startButton.setEnabled(true);
                binding.stopButton.setEnabled(false);

                // Stop the timer
                timerHandler.removeCallbacks(updateTimerThread);
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
                else if (week==2) {
                    Intent intent = new Intent(getActivity(), MadHouse_2.class);
                    startActivity(intent);
                }
            }
        });
        return binding.getRoot();
    }


}
