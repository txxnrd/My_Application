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
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Context;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import com.bumptech.glide.Glide;
import android.content.DialogInterface;
import android.view.Window;
import android.view.MotionEvent;
import static android.app.Activity.RESULT_OK;
import android.graphics.Color;
import android.net.Uri;



public class BlankFragment2 extends Fragment implements MyRecyclerAdapter2.ItemClickListener {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter2 mRecyclerAdapter2;
    private ArrayList<BlogItem> mblogItems;
    private static final int REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> launcher;
    private ImageView currentSelectedImageView;
    private boolean isEditable = false;
    int resourceId = R.drawable.default_image;

    Uri uri = Uri.parse("android.resource://com.example.myapplication/" + resourceId);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        Button iconweek = view.findViewById(R.id.iconweek);

        iconweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerAdapter2 = new MyRecyclerAdapter2();
        mRecyclerAdapter2.setClickListener(this);

        mRecyclerView.setAdapter(mRecyclerAdapter2);
        File file = new File(getContext().getFilesDir(), "my_data2.json");
        String filename = "my_data2.json";
        if (!file.exists()) {

            try {
                InputStream inputStream = getContext().getAssets().open("data2.json");
                InputStreamReader reader = new InputStreamReader(inputStream);
                Gson gson = new Gson();
                mblogItems = gson.fromJson(reader, new TypeToken<List<BlogItem>>() {
                }.getType());

                String json = gson.toJson(mblogItems);
                FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
            } catch (IOException e) {
                Log.e("BlankFragment2", "Error loading JSON", e);
            }
        } else {
            String json = null;
            FileInputStream fis;
            try {
                fis = getContext().openFileInput(filename);
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                fis.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException e) {
                Log.e("BlankFragment2", "Error loading data from internal storage", e);
            }
            Gson gson = new Gson();

            mblogItems = gson.fromJson(json, new TypeToken<List<BlogItem>>() {
            }.getType());
        }
        mRecyclerAdapter2.setBlogList(mblogItems);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        BlogItem item = mblogItems.get(position);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.grid_dialog, null);

        EditText titleTextView = dialogView.findViewById(R.id.titleTextView);
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        EditText textTextView = dialogView.findViewById(R.id.textTextView);
        ImageView closeButton = dialogView.findViewById(R.id.button);
        Button editButton = dialogView.findViewById(R.id.editbutton);
        TextView dayTextView = dialogView.findViewById(R.id.day);

        dayTextView.setText(item.getDay());

        if (item != null && item.getDay_image() != null) {
            Glide.with(imageView.getContext())
                    .load(item.getDay_image())
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

                String title = titleTextView.getText().toString();
                String text = textTextView.getText().toString();
                String day = dayTextView.getText().toString();

                if(title.isEmpty()) {title = "제목을 입력해주세요";}
                if(text.isEmpty()) {text = "내용을 입력해주세요";}

                BlogItem blogItem = new BlogItem();
                blogItem.setTitle(title);
                blogItem.setText(text);
                blogItem.setDay(day);
                blogItem.setDay_image(uri.toString());

                // 아이템을 바꿔치기합니다.
                mblogItems.set(position, blogItem);
                mRecyclerAdapter2.notifyDataSetChanged();
                String filename = "my_data2.json";
                Gson gson = new Gson();
                String json = gson.toJson(mblogItems);
                FileOutputStream fos;
                try {
                    fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(json.getBytes());
                    fos.close();
                } catch (IOException e) {
                    Log.e("BlankFragment", "Error saving data to internal storage", e);
                }


                //다이얼로그 종료
                dialog.dismiss();
            }
        });
        textTextView.setEnabled(false);
        titleTextView.setEnabled(false);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isEditable) {
                    // EditText를 비활성화하고 버튼의 색상과 텍스트를 변경합니다.
                    textTextView.setEnabled(false);
                    titleTextView.setEnabled(false);
                    editButton.setBackgroundColor(Color.RED);
                    editButton.setText("Enable edit");
                    imageView.setOnClickListener(null);


                    isEditable = false; // 상태를 업데이트합니다.
                } else {
                    // EditText를 활성화하고 버튼의 색상과 텍스트를 변경합니다.
                    textTextView.setEnabled(true);
                    titleTextView.setEnabled(true);
                    editButton.setBackgroundColor(Color.GREEN);
                    editButton.setText("Disable edit");

                    imageView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            currentSelectedImageView = imageView;
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            String[] mimeTypes = {"image/jpeg", "image/png"};
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            startActivityForResult(intent, 55);
                        }

                    });


                    isEditable = true; // 상태를 업데이트합니다.
                }


                //Intent intent = new Intent(getActivity(), EditpageActivity.class);
                //startActivityForResult(intent, REQUEST_CODE);
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
                        if (event.getY() - downY > CLOSE_THRESHOLD) {
                            dialog.dismiss();
                        }
                        return true;
                }
                return false;
            }
        });




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle the result here...
        if (requestCode == REQUEST_CODE) {
            if (resultCode == DialogActivity.RESULT_OK) {
                BlogItem newPost = data.getParcelableExtra("newpost");
                // Assuming result is a JSON representation of a FriendItem
                mblogItems.add(newPost);
                mRecyclerAdapter2.setBlogList(mblogItems);
                mRecyclerAdapter2.notifyDataSetChanged();
                String filename = "my_data2.json";
                Gson gson = new Gson();
                String json = gson.toJson(mblogItems);
                FileOutputStream fos;
                try {
                    fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(json.getBytes());
                    fos.close();
                } catch (IOException e) {
                    Log.e("BlankFragment", "Error saving data to internal storage", e);
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 55) {
            if(currentSelectedImageView != null && isEditable) {
                // 갤러리 앱에서 이미지를 선택하고 돌아왔을 때 해당 이미지를 imageView에 셋팅
                currentSelectedImageView.setImageURI(data.getData());
                currentSelectedImageView = null;
                uri = data.getData();
            }
        }
    }


}