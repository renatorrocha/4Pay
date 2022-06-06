package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.appbanco.databinding.ActivityPixTransfSucessoBinding;
import com.example.appbanco.view.Home.Home;

public class PixTransfSucesso extends AppCompatActivity {

    private ActivityPixTransfSucessoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfSucessoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PixTransfSucesso.this, Home.class);
                startActivity(intent);
            }
        });
    }
}