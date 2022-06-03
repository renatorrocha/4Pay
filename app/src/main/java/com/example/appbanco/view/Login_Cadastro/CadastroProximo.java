package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.appbanco.databinding.ActivityCadastroProximoBinding;

public class CadastroProximo extends AppCompatActivity {

    private ActivityCadastroProximoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroProximoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroProximo.this, TipoConta.class);
                startActivity(intent);
            }
        });
    }
}