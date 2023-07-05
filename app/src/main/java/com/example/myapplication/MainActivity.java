package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.myapplication.BlankFragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

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

        ViewPager viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.Smenu);

        viewPager.setOffscreenPageLimit(2);

        // Initialize ViewPager and BottomNavigationView


// Set a PageTransformer that will be called for each attached page
        //viewPager.setPageTransformer(true, new DepthPageTransformer());

// Set an adapter for the ViewPager
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

// Set a listener that will be invoked whenever the page changes or is incrementally scrolled
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // When the page is selected, change the selected item on the Bottom Navigation
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
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
        // Set a listener to be notified when any menu item is clicked.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // When the Bottom Navigation item is selected, switch to the corresponding page in the ViewPager
                int id = item.getItemId();

                if (id == R.id.tab1) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (id == R.id.tab2) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (id == R.id.tab3) {
                    viewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            FriendItem newPerson = (FriendItem) data.getParcelableExtra("newperson");
            Bundle args = new Bundle();
            args.putParcelable("newPerson", newPerson);
            blankfragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.containers, blankfragment).commit();

        }

 */
    }
}