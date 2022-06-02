package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class PixTransfDestino extends AppCompatActivity {

    Button btnPixFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_transf_destino);

        btnPixFinal = findViewById(R.id.btnPixFinal);

        btnPixFinal.setOnClickListener(view -> {
            startActivity(new Intent(this, PixTransfFinal.class));
        });
    }
}