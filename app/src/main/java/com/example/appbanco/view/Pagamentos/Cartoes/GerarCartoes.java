package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.appbanco.R;

public class GerarCartoes extends AppCompatActivity {

    Button btnGerarCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_cartoes);

        btnGerarCartao = findViewById(R.id.btnGerarCartao);

        btnGerarCartao.setOnClickListener(view1 -> {
            startActivity(new Intent(this, CartaoCriado.class));
        });
    }
}