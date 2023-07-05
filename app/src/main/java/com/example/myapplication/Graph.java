package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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
        entries.add(new BarEntry(0, 20));
        entries.add(new BarEntry(1, elapsedTimeWeek2));
        entries.add(new BarEntry(2, elapsedTimeWeek3));
        entries.add(new BarEntry(3, elapsedTimeWeek4));
        entries.add(new BarEntry(4, elapsedTimeWeek5));

        BarDataSet barDataSet = new BarDataSet(entries, "Elapsed Time");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Define the labels for the X axis
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Week 1");
        labels.add("Week 2");
        labels.add("Week 3");
        labels.add("Week 4");
        labels.add("Week 5");

        // Set the labels to the X axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // To display labels at the bottom
        xAxis.setDrawGridLines(false); // To hide grid lines

        barChart.invalidate(); // refresh chart
    }

}
