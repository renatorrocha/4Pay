package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TipoChave extends AppCompatActivity {

    private ActivityTipoChaveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipoChaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTipoChaveProximo.setOnClickListener(view -> {
            startActivity(new Intent(this, ChaveCriada.class));
        });
    }
}