package com.example.appbanco.view.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.databinding.ActivityCriaChavePixBinding;
import com.example.appbanco.view.Pagamentos.Pix.PixTransfDestino;

public class CriaChavePix extends AppCompatActivity {

    private ActivityCriaChavePixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriaChavePixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriaChavePix.setOnClickListener(view -> {
            startActivity(new Intent(this, TipoChave.class));
        });
    }
}