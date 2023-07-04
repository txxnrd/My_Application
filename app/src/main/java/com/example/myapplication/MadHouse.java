package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.Fragment3Binding;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MadHouse extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madhouse);

        Button button = (Button) findViewById(R.id.button2); // Replace with your actual Button ID
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MadHouse.this, MadHouse_2.class);
                startActivity(intent);
            }
        });


        // Load the last elapsed time from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        long lastElapsedTime = (sharedPreferences.getLong("lastElapsedTime", 0))*-1; // Don't divide by 1000 here

// Convert milliseconds to hours, minutes, and seconds
        long hours = TimeUnit.MILLISECONDS.toHours(lastElapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(lastElapsedTime) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(lastElapsedTime));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(lastElapsedTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lastElapsedTime));

// Now the time should be positive
        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        TextView textView = findViewById(R.id.time_history); // Replace "your_textview_id" with your actual TextView ID
        textView.setText(formattedTime);


        // Get the current date
        Calendar currentCalendar = Calendar.getInstance();

        // Get the start date (2023-06-29)
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, 5, 29); // Month is 0-based, so 5 is June

        // Calculate the week
        int week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;





        TextView textView_1 = findViewById(R.id.dateTextView); // Replace with your actual TextView ID
        textView_1.setText(String.valueOf(week)+"주차 기록");
    }

}

