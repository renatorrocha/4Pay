package com.example.appbanco.view.Pagamentos.Cartoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.databinding.ActivityCartaoCriadoBinding;
import com.example.appbanco.view.Home.Home;

public class CartaoCriado extends AppCompatActivity {

    private ActivityCartaoCriadoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoCriadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartaoCriado.this, Home.class);
                startActivity(intent);
            }
        });
    }
}