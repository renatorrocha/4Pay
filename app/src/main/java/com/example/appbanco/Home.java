package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.databinding.ActivityHomeBinding;
import com.example.appbanco.databinding.ActivityLoginBinding;

public class Home extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearLayout.setOnClickListener(view1 -> {
            startActivity(new Intent(this, HomeConfigs.class));
        });

//        binding.tvExtrato.setOnClickListener(view1 -> {
//            startActivity(new Intent(this, ));
//        });

        binding.tvPix.setOnClickListener(view1 -> {
            startActivity(new Intent(this, PixTransf.class));
        });

//        binding.tvPagamentos.setOnClickListener(view1 -> {
//            startActivity(new Intent(this, ));
//        });

        binding.tvCartao.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Cartoes.class));
        });

        binding.tvTransf.setOnClickListener(view1 -> {
            startActivity(new Intent(this, PixTransf.class));
        });

        binding.tvRecarga.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Recarga.class));
        });

    }
}