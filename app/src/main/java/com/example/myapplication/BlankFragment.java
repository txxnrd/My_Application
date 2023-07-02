package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment implements MyRecyclerAdapter.ItemClickListener {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerAdapter = new MyRecyclerAdapter();
        mRecyclerAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mRecyclerAdapter);

        try {
            InputStream inputStream = requireActivity().getAssets().open("data.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();

            mfriendItems = gson.fromJson(reader, new TypeToken<List<FriendItem>>() {}.getType());


        } catch (IOException e) {
            Log.e("BlankFragment", "Error loading JSON", e);
        }
//왜 푸시가 안되지
        mRecyclerAdapter.setFriendList(mfriendItems);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Gson gson = new Gson();
        String json = gson.toJson(mfriendItems);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("data",json);
        startActivity(intent);
    }
}