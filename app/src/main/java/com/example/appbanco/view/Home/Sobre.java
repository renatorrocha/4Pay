package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.appbanco.R;

public class Sobre extends AppCompatActivity {

    ImageView ivArrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        ivArrowBack = findViewById(R.id.ivArrowBack);

        ivArrowBack.setOnClickListener(v -> {
            finish();
        });
    }
}