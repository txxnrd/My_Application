package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        BarChart barChart = findViewById(R.id.barChart);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        long elapsedTimeWeek1 = sharedPreferences.getLong("elapsedTimeWeek1", 0);
        long elapsedTimeWeek2 = sharedPreferences.getLong("elapsedTimeWeek2", 0);
        long elapsedTimeWeek3 = sharedPreferences.getLong("elapsedTimeWeek3", 0);
        long elapsedTimeWeek4 = sharedPreferences.getLong("elapsedTimeWeek4", 0);
        long elapsedTimeWeek5 = sharedPreferences.getLong("elapsedTimeWeek5", 0);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 20));
        entries.add(new BarEntry(2, elapsedTimeWeek2));
        entries.add(new BarEntry(3, elapsedTimeWeek3));
        entries.add(new BarEntry(4, elapsedTimeWeek4));
        entries.add(new BarEntry(5, elapsedTimeWeek5));

        BarDataSet barDataSet = new BarDataSet(entries, "Elapsed Time");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh chart
    }

}


