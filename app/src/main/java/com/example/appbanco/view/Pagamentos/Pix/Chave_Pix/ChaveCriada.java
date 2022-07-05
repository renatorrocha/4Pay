package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityChaveCriadaBinding;
import com.example.appbanco.view.Home.Home;

public class ChaveCriada extends AppCompatActivity {

    private ActivityChaveCriadaBinding binding;
    private String tipoChave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChaveCriadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tipoChave = getIntent().getStringExtra("tipoChave");

        binding.tipoChave.setText(getString(R.string.txt_apresenta_chave, tipoChave));

        binding.btcChaveConcluir.setOnClickListener(view -> {
            Intent intent = new Intent(ChaveCriada.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}