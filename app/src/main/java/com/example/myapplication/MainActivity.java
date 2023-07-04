package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    BlankFragment blankfragment;
    BlankFragment2 blankfragment2;
    BlankFragment3 blankfragment3;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blankfragment = new BlankFragment();
        blankfragment2 = new BlankFragment2();
        blankfragment3 = new BlankFragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, blankfragment).commit();

        bottomNavigationView = findViewById(R.id.Smenu);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DataHelper dataHelper = DataHelper.getInstance(MainActivity.this);

                // If the timer is not running, switch the screen and disable bottomNavigationView
                if (!dataHelper.isTimerCounting()) {
                    if (id == R.id.tab1) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, blankfragment).commit();

                        return true;
                    } else if (id == R.id.tab2) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, blankfragment2).commit();

                        return true;
                    } else if (id == R.id.tab3) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, blankfragment3).commit();

                        return true;
                    }
                }
                else
                    bottomNavigationView.setEnabled(false);
                return false;
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}