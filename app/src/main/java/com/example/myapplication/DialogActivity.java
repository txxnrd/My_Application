package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import android.net.Uri;

import com.bumptech.glide.Glide;

public class DialogActivity extends AppCompatActivity {
    int resourceId = R.drawable.default_image; // replace 'your_image' with your actual image resource name
    Uri uri = Uri.parse("android.resource://com.example.myapplication/" + resourceId);

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 55) {
            uri = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            Glide.with(imageView.getContext())
                    .load(uri)
                    .into(imageView);
            // selectedImageUri를 이용해 이미지를 처리합니다.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        // Get EditText instances
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        ImageView imageView = findViewById(R.id.imageView);



        Button button = findViewById(R.id.button);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, 55);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String description = editTextDescription.getText().toString();

                if (phone.length() == 10) {
                    phone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6, 10);
                } else if (phone.length() == 11) {
                    phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11);
                }

                FriendItem friendItem = new FriendItem();
                friendItem.setName(name);
                friendItem.setNumber(phone);
                friendItem.setDescription(description);
                friendItem.setImageUrl(uri.toString());
                //friendItem.setImageUrl(name);
                Intent intent = new Intent(DialogActivity.this, MainActivity.class);
                intent.putExtra("newperson", friendItem);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }



}        