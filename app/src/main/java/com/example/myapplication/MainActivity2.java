package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class MainActivity2 extends AppCompatActivity implements MyRecyclerAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyRecyclerAdapter();
        mRecyclerAdapter.setClickListener(this); // MainActivity2 is now listening to click events.

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
        mfriendItems = new ArrayList<>();
        for(int i=1; i<=10; i++){
            if(i%2==0)
                mfriendItems.add(new FriendItem(R.drawable.ic_man, i+"번째 사람", i+"번째 상태메시지"));
            else
                mfriendItems.add(new FriendItem(R.drawable.ic_woman, i+"번째 사람", i+"번째 상태메시지"));
        }

        mRecyclerAdapter.setFriendList(mfriendItems);
        Log.d("MainActivity2", "Size of mfriendItems: " + mfriendItems.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        // Define what happens when an item is clicked. Here we start NewActivity.
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }
}