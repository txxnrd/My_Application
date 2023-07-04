package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MadHouse_2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madhouse2);

        // Load the last elapsed time from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        long lastElapsedTime = sharedPreferences.getLong("lastElapsedTime", 0)/1000;
        long second = lastElapsedTime % 60;
        long minute = (lastElapsedTime / 60) % 60;
        long hour = lastElapsedTime / 3600;


       // Get the current date
        Calendar currentCalendar = Calendar.getInstance();

        // Get the start date (2023-06-29)
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, 5, 29); // Month is 0-based, so 5 is June

        // Calculate the week
        int week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;


        // Display the last elapsed time
        TextView textView = findViewById(R.id.time_history);

        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
        textView.setText(formattedTime);

        TextView textView_1 = findViewById(R.id.dateTextView); // Replace with your actual TextView ID
        textView_1.setText(String.valueOf(week)+"아하하하ㅏ핳");
    }
}

