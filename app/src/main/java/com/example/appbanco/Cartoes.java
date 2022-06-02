package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

public class Cartoes extends AppCompatActivity {

    private ConstraintLayout gerarCartão;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoes);

        gerarCartão = findViewById(R.id.clGerarCartao);

        gerarCartão.setOnClickListener(view1 -> {
            Intent it = new Intent(this, GerarCartoes.class);
            startActivity(it);
        });
    }
}