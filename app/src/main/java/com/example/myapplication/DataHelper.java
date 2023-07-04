package com.example.myapplication;

//package code.with.cal.persistenttimerapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataHelper {
    private static final String PREFERENCES = "prefs";
    private static final String START_TIME_KEY = "startKey";
    private static final String STOP_TIME_KEY = "stopKey";
    private static final String COUNTING_KEY = "countingKey";

    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

    private boolean timerCounting;
    private Date startTime;
    private Date stopTime;

    public DataHelper(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        timerCounting = sharedPref.getBoolean(COUNTING_KEY, false);

        String startString = sharedPref.getString(START_TIME_KEY, null);
        if (startString != null)
            try {
                startTime = dateFormat.parse(startString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        String stopString = sharedPref.getString(STOP_TIME_KEY, null);
        if (stopString != null)
            try {
                stopTime = dateFormat.parse(stopString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date date) {
        startTime = date;
        SharedPreferences.Editor editor = sharedPref.edit();
        String stringDate = (date == null) ? null : dateFormat.format(date);
        editor.putString(START_TIME_KEY, stringDate);
        editor.apply();
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date date) {
        stopTime = date;
        SharedPreferences.Editor editor = sharedPref.edit();
        String stringDate = (date == null) ? null : dateFormat.format(date);
        editor.putString(STOP_TIME_KEY, stringDate);
        editor.apply();
    }

    public boolean isTimerCounting() {
        return timerCounting;
    }

    public void setTimerCounting(boolean value) {
        timerCounting = value;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(COUNTING_KEY, value);
        editor.apply();
    }

}

