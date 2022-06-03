package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityTipoContaBinding;
import com.example.appbanco.view.Home.Home;

public class TipoConta extends AppCompatActivity {

    private ActivityTipoContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_conta);

        binding = ActivityTipoContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipoConta.this, Home.class);
                startActivity(intent);
            }
        });
    }
}