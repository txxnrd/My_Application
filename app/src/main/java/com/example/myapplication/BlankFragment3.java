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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BlankFragment3 extends Fragment {

    private Fragment3Binding binding;
    private long elapsedTime=0L;
    private DataHelper dataHelper;

    boolean changed_1=false,changed_2=false;

    long startTime = 0L;
    long pauseOffset=0;
    private Handler timerHandler = new Handler();
    private Runnable updateTimerThread;

    int week;

    private static final long TIME_THRESHOLD = 2 * 1000;
    private static final long TIME_THRESHOLD_2 = 4 * 1000;
    private static final int DEFAULT_IMAGE = R.drawable.character_start;
    private static final int NEW_IMAGE = R.drawable.character_1;
    private static final int NEW_IMAGE_2 = R.drawable.character;

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

        week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = formatter.format(currentCalendar.getTime());

        dateTextView.setText(week+"주차"+"    "+formattedDate);

        binding.imageView.setImageResource(DEFAULT_IMAGE);

        binding.startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (binding.startButton.getText().toString().equalsIgnoreCase("start")) {
                    startTime = SystemClock.elapsedRealtime();
                    dataHelper.setStartTime(new Date(startTime));
                    dataHelper.setTimerCounting(true);

                    binding.startButton.setText("Stop");
                    binding.chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    binding.chronometer.start();

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
