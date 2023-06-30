package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@linkFragment} subclass.
 * Use the {@linkBlankFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_2,container,false);
        //return rootView;
        View view = inflater.inflate(R.layout.fragment_1,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /* initiate recyclerview */
        mRecyclerAdapter = new MyRecyclerAdapter();
        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /* adapt data */
        mfriendItems = new ArrayList<>();
        for(int i=1; i<=10; i++){
            if(i%2==0)
                mfriendItems.add(new FriendItem(R.drawable.ic_man, i+"번째 사람", i+"번째 상태메시지"));
            else
                mfriendItems.add(new FriendItem(R.drawable.ic_woman, i+"번째 사람", i+"번째 상태메시지"));
        }

        mRecyclerAdapter.setFriendList(mfriendItems);
        Log.d("BlankFragment", "Size of mfriendItems: " + mfriendItems.size());

        return view;
    }

    /*
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_1);

        //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
    /*
        mRecyclerAdapter = new MyRecyclerAdapter();

        /* initiate recyclerview */
    /*
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /* adapt data */
    /*
        mfriendItems = new ArrayList<>();
        for(int i=1; i<=10; i++){
            if(i%2==0)
                mfriendItems.add(new FriendItem(R.drawable.ic_man, i+"번째 사람", i+"번째 상태메시지"));
            else
                mfriendItems.add(new FriendItem(R.drawable.ic_woman, i+"번째 사람", i+"번째 상태메시지"));
        }

        mRecyclerAdapter.setFriendList(mfriendItems);
        Log.d("BlankFragment", "Size of mfriendItems: " + mfriendItems.size());

    }
    */
}