package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

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
import android.app.Dialog;
import com.bumptech.glide.Glide;
import android.content.DialogInterface;
import android.view.Window;
import android.view.MotionEvent;



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
        FriendItem item = mfriendItems.get(position);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_main3, null);

        TextView nameTextView = dialogView.findViewById(R.id.nameTextView);
        TextView phonenumber = dialogView.findViewById(R.id.phonenumber);
        TextView descriptionTextView = dialogView.findViewById(R.id.descriptionTextView);
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        ImageView closeButton = dialogView.findViewById(R.id.button);

        nameTextView.setText(item.getName());
        phonenumber.setText(item.getNumber());
        descriptionTextView.setText(item.getDescription());
        Glide.with(imageView.getContext())
                .load(item.getImageUrl())
                .into(imageView);
        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();  // 이 코드가 Dialog를 닫는 코드입니다
            }
        });
        dialog.setContentView(dialogView);
        dialog.show();
        dialogView.setOnTouchListener(new View.OnTouchListener() {
            float downY = 0;
            final int CLOSE_THRESHOLD = 50;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (downY - event.getY() > CLOSE_THRESHOLD) {
                            dialog.dismiss();
                        }
                        return true;
                }
                return false;
            }
        });

    }
}