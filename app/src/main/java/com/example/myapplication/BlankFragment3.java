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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import com.example.myapplication.databinding.Fragment3Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BlankFragment3 extends Fragment {

    private Fragment3Binding binding;
    private DataHelper dataHelper;



    long startTime = 0L;
    long pauseOffset=0;
    private Handler timerHandler = new Handler();
    private Runnable updateTimerThread;
    boolean changed_1=false,changed_2=false;
    int week;

    private static final long TIME_THRESHOLD = 2 * 1000;
    private static final long TIME_THRESHOLD_2 = 4 * 1000;
    private static final int DEFAULT_IMAGE = R.drawable.character_start;
    private static final int NEW_IMAGE = R.drawable.character_1;
    private static final int NEW_IMAGE_2 = R.drawable.character;
    private static long elapsedTime = 0L;
    private Calendar startCalendar = Calendar.getInstance();
    private Runnable checkWeekChange = new Runnable() {
        int previousWeek = week;
        public void run() {
            Calendar currentCalendar = Calendar.getInstance();
            week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                    (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;
            if (week != previousWeek) {
                //long elapsedChronoTime = SystemClock.elapsedRealtime() - binding.chronometer.getBase();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("elapsedTimeWeek" + week, elapsedTime);
                editor.apply();

                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                elapsedTime = 0;
                previousWeek = week;
            }
            timerHandler.postDelayed(this, 24*60*60*1000);  // Check every day
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = Fragment3Binding.inflate(inflater, container, false);
        dataHelper = new DataHelper(getActivity());

        TextView dateTextView = binding.dateTextView;



        Calendar currentCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, 5, 29);

        timerHandler.postDelayed(checkWeekChange, 24*60*60*1000);

        week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = formatter.format(currentCalendar.getTime());

        dateTextView.setText(week+"주차"+"    "+formattedDate);

        if (elapsedTime >= TIME_THRESHOLD_2) {
            binding.imageView.setImageResource(NEW_IMAGE_2);
            changed_2=true;
        } else if (elapsedTime >= TIME_THRESHOLD) {
            binding.imageView.setImageResource(NEW_IMAGE);
            changed_1=true;
        } else binding.imageView.setImageResource(DEFAULT_IMAGE);

        binding.startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BottomNavigationView navigationView = getActivity().findViewById(R.id.Smenu);  // Assuming 'navigation' is the id of your BottomNavigationView

                if (binding.startButton.getText().toString().equalsIgnoreCase("start")) {
                    startTime = SystemClock.elapsedRealtime();
                    dataHelper.setStartTime(new Date(startTime));
                    dataHelper.setTimerCounting(true);

                    binding.startButton.setText("Stop");

                    binding.chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    elapsedTime = SystemClock.elapsedRealtime() - pauseOffset;
                    binding.chronometer.start();


                    // Save elapsedTime in SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("elapsedTimeWeek"+ week, elapsedTime);
                    editor.apply();

                    // Disabling BottomNavigationView when the timer starts
                    navigationView.setEnabled(false);
                    for (int i = 0; i < navigationView.getMenu().size(); i++) {
                        navigationView.getMenu().getItem(i).setEnabled(false);
                    }

                    updateTimerThread = new Runnable() {
                        public void run() {
                            elapsedTime = SystemClock.elapsedRealtime() - startTime + pauseOffset;

                            // Save elapsedTime in SharedPreferences
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("elapsedTime", elapsedTime);
                            editor.apply();

                            if (elapsedTime >= TIME_THRESHOLD_2 && !changed_2) {
                                binding.imageView.setImageResource(NEW_IMAGE_2);
                                changed_2=true;
                            } else if (elapsedTime >= TIME_THRESHOLD && !changed_1) {
                                binding.imageView.setImageResource(NEW_IMAGE);
                                changed_1=true;
                            }
                            timerHandler.postDelayed(this, 1000);
                        }
                    };
                    timerHandler.postDelayed(updateTimerThread, 1000);


                } else {
                    pauseOffset = SystemClock.elapsedRealtime() - binding.chronometer.getBase();
                    dataHelper.setStopTime(new Date(SystemClock.elapsedRealtime()));
                    dataHelper.setTimerCounting(false);

                    binding.chronometer.stop();

                    timerHandler.removeCallbacks(updateTimerThread);

                    binding.startButton.setText("Start");

                    // Enabling BottomNavigationView when the timer stops
                    navigationView.setEnabled(true);
                    for (int i = 0; i < navigationView.getMenu().size(); i++) {
                        navigationView.getMenu().getItem(i).setEnabled(true);
                    }
                }
            }
        });

        binding.buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startButton.getText().toString().equalsIgnoreCase("Stop")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Stop the Stopwatch")
                            .setMessage("Please stop the stopwatch before proceeding.")
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    if (week == 1) {
                        Intent intent = new Intent(getActivity(), MadHouse.class);
                        startActivity(intent);
                    }
                }
            }
        });
        binding.buttonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.startButton.getText().toString().equalsIgnoreCase("Stop")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Stop the Stopwatch")
                            .setMessage("Please stop the stopwatch before proceeding.")
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {

                    Intent intent = new Intent(getActivity(), Graph.class);
                    startActivity(intent);
                }
            }
        });

        // If timer was counting before, restore it
        if (dataHelper.isTimerCounting()) {
            Date prevStart = dataHelper.getStartTime();
            if (prevStart != null) {
                startTime = prevStart.getTime();
                pauseOffset = SystemClock.elapsedRealtime() - startTime;
                binding.chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                binding.startButton.setText("Stop");
                binding.chronometer.start();
            }
        }


        return binding.getRoot();
    }
}
