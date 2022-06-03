package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.R;

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