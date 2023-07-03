package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class BlankFragment3 extends Fragment {

    private long startTime = 0L;
    private long elapsedTime = 0L;
    private Handler timerHandler = new Handler();
    private Runnable updateTimerThread;

    private static final long TIME_THRESHOLD = 2 * 1000; // 2초(밀리초 단위)
    private static final long TIME_THRESHOLD_2 = 4 * 1000; // 4초(밀리초 단위)
    private static final int DEFAULT_IMAGE = R.drawable.character_start;
    private static final int NEW_IMAGE = R.drawable.character_1;
    private static final int NEW_IMAGE_2 = R.drawable.character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Fragment3Binding binding = Fragment3Binding.inflate(inflater, container, false);
        binding.imageView.setImageResource(DEFAULT_IMAGE);

        // Start button
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chronometer.setBase(SystemClock.elapsedRealtime() + elapsedTime);
                binding.chronometer.start();
                binding.startButton.setEnabled(false);
                binding.resetButton.setEnabled(true);
                binding.stopButton.setEnabled(true);
                startTime = SystemClock.elapsedRealtime();

                // Timer Thread
                updateTimerThread = new Runnable() {
                    public void run() {
                        elapsedTime = SystemClock.elapsedRealtime() - startTime;
                        if (elapsedTime >= TIME_THRESHOLD_2) {
                            binding.imageView.setImageResource(NEW_IMAGE_2);
                        } else if (elapsedTime >= TIME_THRESHOLD) {
                            binding.imageView.setImageResource(NEW_IMAGE);
                        }
                        timerHandler.postDelayed(this, 1000); // Check every second
                    }
                };
                timerHandler.postDelayed(updateTimerThread, 1000); // Delay
            }
        });

        // Stop button hohotoh
        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime = binding.chronometer.getBase() - SystemClock.elapsedRealtime();
                binding.chronometer.stop();
                binding.startButton.setEnabled(true);
                binding.resetButton.setEnabled(true);
                binding.stopButton.setEnabled(false);
                timerHandler.removeCallbacks(updateTimerThread); // Stop the timer
            }
        });

        // Reset button
        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elapsedTime = 0L;
                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                binding.chronometer.stop();
                binding.startButton.setEnabled(true);
                binding.resetButton.setEnabled(false);
                binding.stopButton.setEnabled(false);
                binding.imageView.setImageResource(DEFAULT_IMAGE);
                timerHandler.removeCallbacks(updateTimerThread); // Stop the timer
            }
        });

        return binding.getRoot();
    }
}
