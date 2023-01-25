package com.example.cameraaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DennoActivity extends AppCompatActivity {
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denno);
       textView = findViewById(R.id.textDeno);
        textView.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
    }
