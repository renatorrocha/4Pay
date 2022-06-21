package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityCartaoFaturaBinding;

public class CartaoFatura extends AppCompatActivity {

    ActivityCartaoFaturaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoFaturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(view -> {});
        finish();
    }
}