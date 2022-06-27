package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagarBoletoBinding;

public class PagarBoleto extends AppCompatActivity {


    private ActivityPagarBoletoBinding binding;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarBoletoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(v -> {

        });
    }
}