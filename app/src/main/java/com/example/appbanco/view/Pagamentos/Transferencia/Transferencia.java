package com.example.appbanco.view.Pagamentos.Transferencia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;

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