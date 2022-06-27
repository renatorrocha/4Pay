package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagamentoBinding;

public class Pagamento extends AppCompatActivity {

    private ActivityPagamentoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(v -> {
            finish();
        });

        binding.clBoleto.setOnClickListener(v -> {

        });
    }
}