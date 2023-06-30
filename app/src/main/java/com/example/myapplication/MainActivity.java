package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    BlankFragment blankfragment;
    BlankFragment2 blankfragment2;
    BlankFragment3 blankfragment3;

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

                return false;
            }
        });

    }
}