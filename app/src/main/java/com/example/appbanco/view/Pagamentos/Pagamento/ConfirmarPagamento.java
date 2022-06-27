package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityConfirmarPagamentoBinding;

public class ConfirmarPagamento extends AppCompatActivity {

    private ActivityConfirmarPagamentoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmarPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(v -> {
            finish();
        });
    }
}