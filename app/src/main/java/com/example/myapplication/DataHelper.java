package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataHelper {
    private static DataHelper instance;
    private static final String PREFERENCES = "prefs";
    private static final String START_TIME_KEY = "startKey";
    private static final String STOP_TIME_KEY = "stopKey";
    private static final String COUNTING_KEY = "countingKey";

    private SharedPreferences sharedPref;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

    private boolean timerCounting;
    private Date startTime;
    private Date stopTime;
    private long pauseOffset;

    DataHelper(Context context) {
        sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        timerCounting = sharedPref.getBoolean(COUNTING_KEY, true);

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

    public static synchronized DataHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataHelper(context.getApplicationContext());
        }
        return instance;
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

    // This method gets the boolean value
    public boolean getChangedStatus(String key) {
        return sharedPref.getBoolean(key, false);
    }

    // This method saves the boolean value
    public void setChangedStatus(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
