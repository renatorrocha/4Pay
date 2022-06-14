package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.databinding.ActivityCriaChavePixBinding;

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