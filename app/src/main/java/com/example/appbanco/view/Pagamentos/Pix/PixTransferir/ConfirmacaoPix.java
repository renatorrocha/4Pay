package com.example.appbanco.view.Pagamentos.Pix.PixTransferir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.databinding.ActivityConfirmacaoPixBinding;


public class ConfirmacaoPix extends AppCompatActivity {

    private ActivityConfirmacaoPixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmacaoPixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmacaoPix.this, PixTransfSucesso.class);
                startActivity(intent);
            }
        });
    }
}