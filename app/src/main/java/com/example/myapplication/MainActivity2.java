package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        /* load JSON from assets */
        try {
            InputStream inputStream = getAssets().open("data.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();

            // This will convert the JSON array into a list of FriendItems
            mfriendItems = gson.fromJson(reader, new TypeToken<List<FriendItem>>() {}.getType());

        } catch (IOException e) {
            Log.e("MainActivity2", "Error loading JSON", e);
        }

        /* adapt data */
        mRecyclerAdapter.setFriendList(mfriendItems);
    }

    @Override
    public void onItemClick(View view, int position) {
        // Define what happens when an item is clicked. Here we start NewActivity.
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }
}
