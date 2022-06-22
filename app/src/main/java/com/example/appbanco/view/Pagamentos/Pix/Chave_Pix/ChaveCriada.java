package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.databinding.ActivityChaveCriadaBinding;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransfSucesso;

public class ChaveCriada extends AppCompatActivity {

    private ActivityChaveCriadaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChaveCriadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btcChaveConcluir.setOnClickListener(view -> {
            Intent intent = new Intent(ChaveCriada.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}