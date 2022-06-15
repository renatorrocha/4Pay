package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;

public class Cartoes extends AppCompatActivity {

    private ConstraintLayout gerarCartão;
    private ImageView ivArrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoes);

        gerarCartão = findViewById(R.id.clGerarCartao);
        ivArrowBack = findViewById(R.id.ivArrowBack);

        gerarCartão.setOnClickListener(view1 -> {
            Intent it = new Intent(this, GerarCartoes.class);
            startActivity(it);
        });

        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });
    }
}