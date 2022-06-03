package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.databinding.ActivityEsqueciSenhaBinding;
import com.example.appbanco.view.Home.Home;

public class EsqueciSenha extends AppCompatActivity {

    private ActivityEsqueciSenhaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEsqueciSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EsqueciSenha.this, Home.class);
                startActivity(intent);
                Toast.makeText(EsqueciSenha.this, "Um email de recuperacao foi enviado", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvEnviarDenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EsqueciSenha.this, "Enviamos outro email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}