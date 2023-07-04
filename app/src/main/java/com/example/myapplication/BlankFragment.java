package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


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
import static android.app.Activity.RESULT_OK;




public class BlankFragment extends Fragment implements MyRecyclerAdapter.ItemClickListener,MyRecyclerAdapter.ItemLongClickListener  {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<FriendItem> mfriendItems;
    private static final int REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle the result here...
        if (requestCode == REQUEST_CODE) {
            if (resultCode == DialogActivity.RESULT_OK) {
                FriendItem newPerson = data.getParcelableExtra("newperson");
                // Assuming result is a JSON representation of a FriendItem
                mfriendItems.add(newPerson);
                mRecyclerAdapter.setFriendList(mfriendItems);
                mRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        Button iconplus = view.findViewById(R.id.iconplus);

        iconplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerAdapter = new MyRecyclerAdapter();
        mRecyclerAdapter.setClickListener(this);
        mRecyclerAdapter.setLongClickListener(this);

        mRecyclerView.setAdapter(mRecyclerAdapter);

        try {
            InputStream inputStream = requireActivity().getAssets().open("data.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();


            mfriendItems = gson.fromJson(reader, new TypeToken<List<FriendItem>>() {}.getType());

        } catch (IOException e) {
            Log.e("BlankFragment", "Error loading JSON", e);
        }
        mRecyclerAdapter.setFriendList(mfriendItems);
        return view;
    }
    public void onItemLongClick(View view, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext()); // AlertDialog를 만들기 위한 빌더 얻기
        builder.setTitle("프로필 삭제"); // 대화상자 제목 설정
        builder.setMessage("정말로 삭제하시겠습니까?"); // 대화상자 메시지 설정
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() { // 버튼 추가
            public void onClick(DialogInterface dialog, int id) {
                mfriendItems.remove(position);
                mRecyclerAdapter.setFriendList(mfriendItems);
                mRecyclerAdapter.notifyDataSetChanged();

            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() { // 버튼 추가
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        AlertDialog dialog = builder.create(); // 대화상자 생성
        dialog.show(); // 대화상자 표시
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
        if (item != null && item.getImageUrl() != null) {
            Glide.with(imageView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.default_image)
                    .into(imageView);
        } else {
            // Handle the case where item or imageUrl is null
            Glide.with(imageView.getContext())
                    .load(R.drawable.default_image)
                    .into(imageView);
        }
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
            final int CLOSE_THRESHOLD = 50;  // Threshold in pixels to close the dialog

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