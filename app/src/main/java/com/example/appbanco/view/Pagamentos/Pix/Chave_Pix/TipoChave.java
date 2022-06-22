package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.databinding.ActivityTipoChaveBinding;
import com.example.appbanco.view.Pagamentos.Pix.Pix;

public class TipoChave extends AppCompatActivity {

    private ActivityTipoChaveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipoChaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriarChave.setOnClickListener(view -> {
            startActivity(new Intent(this, ChaveCriada.class));
        });

        binding.ivArrowBack.setOnClickListener(view -> {
            startActivity(new Intent(this, Pix.class));
        });
    }
}