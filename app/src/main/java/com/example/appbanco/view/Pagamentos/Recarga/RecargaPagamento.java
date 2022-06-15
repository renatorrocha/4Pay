package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaPagamentoBinding;

public class RecargaPagamento extends AppCompatActivity {

    private ActivityRecargaPagamentoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_pagamento);

        binding = ActivityRecargaPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSeusSeguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rbtnCartao.isChecked()){
                    Intent intent = new Intent(RecargaPagamento.this,RecargaValorCartao.class);
                    startActivity(intent);
                } else if (binding.rbtnConta.isChecked()){
                    Intent intent = new Intent(RecargaPagamento.this,RecargaValorConta.class);
                    startActivity(intent);
                }
            }
        });
    }
}