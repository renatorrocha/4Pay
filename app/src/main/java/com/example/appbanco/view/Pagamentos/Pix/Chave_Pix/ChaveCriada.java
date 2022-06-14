package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.databinding.ActivityChaveCriadaBinding;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Pix.PixTransf;

public class ChaveCriada extends AppCompatActivity {

    private ActivityChaveCriadaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChaveCriadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btcChaveConcluir.setOnClickListener(view -> {
            startActivity(new Intent(ChaveCriada.this, Pix.class));
        });
    }
}