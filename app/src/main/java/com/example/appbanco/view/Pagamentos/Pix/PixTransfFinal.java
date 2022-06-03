package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;

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