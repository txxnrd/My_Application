package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView detail_id_text;
    String id, name, age, sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //detail_id_text = findViewById(R.id.detail_id_text);
        Intent intent = getIntent();

        id = intent.getExtras().getString("id");

        detail_id_text.setText(id);
    }
}