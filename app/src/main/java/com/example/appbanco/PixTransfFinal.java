package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class PixTransfFinal extends AppCompatActivity {

    Button btnTransf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_transf_final);

        btnTransf = findViewById(R.id.btnTransf);

        btnTransf.setOnClickListener(view -> {
            startActivity(new Intent(this, Home.class));
            Toast.makeText(this, "Transferencia realizada.", Toast.LENGTH_SHORT).show();
        });
    }
}