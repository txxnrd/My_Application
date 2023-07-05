package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import com.example.myapplication.databinding.Fragment3Binding;


import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MadHouse_2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madhouse2);

        ImageButton button1 = (ImageButton) findViewById(R.id.button1); // Replace with your actual Button ID
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MadHouse_2.this, MadHouse.class);
                startActivity(intent);
            }
        });
        ImageButton button2 = (ImageButton) findViewById(R.id.button2); // Replace with your actual Button ID
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MadHouse_2.this, MadHouse_3.class);
                startActivity(intent1);
            }
        });



       // Get the current date
        Calendar currentCalendar = Calendar.getInstance();

        // Get the start date (2023-06-29)
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2023, 5, 29); // Month is 0-based, so 5 is June

        // Calculate the week
        int week = ((int) ((currentCalendar.getTime().getTime() / (1000*60*60*24)) -
                (int) (startCalendar.getTime().getTime() / (1000*60*60*24))) / 7) + 1;


        TextView textView_1 = findViewById(R.id.time_history); // Replace with your actual TextView ID
// Get the current date



        startCalendar.set(2023, 5, 29); // Month is 0-based, so 5 is June



        TextView textView_2 = findViewById(R.id.dateTextView); // Replace with your actual TextView IDs
        textView_2.setText("2주차 몰입이");

        // Load elapsedTime from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        long elapsedTime;
        if (week == 2) {
            elapsedTime = sharedPreferences.getLong("elapsedTime", 0);
        } else {
            elapsedTime = sharedPreferences.getLong("elapsedTimeWeek2", 0);
        }
        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(elapsedTime),
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsedTime)),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)));

        textView_1.setText(formattedTime);
        ImageView imageView = findViewById(R.id.imageView); // Replace with your actual ImageView ID
        if (elapsedTime <= 2000*10) {
            imageView.setImageResource(R.drawable.character_start); // Replace with your actual image resources
        } else if (elapsedTime <= 4000*10) {
            imageView.setImageResource(R.drawable.character_1);
        } else {
            imageView.setImageResource(R.drawable.character);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("elapsedTime", 0);
        editor.apply();
    }

}


