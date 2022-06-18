package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityNotificacoesBinding;

public class Notificacoes extends AppCompatActivity {


    ActivityNotificacoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}