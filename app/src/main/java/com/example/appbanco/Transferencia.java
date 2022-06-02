package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class Transferencia extends AppCompatActivity {

    Button btnTransf2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        btnTransf2 = findViewById(R.id.btnTransf2);

        btnTransf2.setOnClickListener(view -> {
            startActivity(new Intent(this, Home.class));
            Toast.makeText(this, "Transferencia realizada.", Toast.LENGTH_SHORT).show();
        });
    }
}