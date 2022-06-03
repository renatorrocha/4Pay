package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityOperadoraBinding;

public class Operadora extends AppCompatActivity {

    private ActivityOperadoraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operadora);

        binding = ActivityOperadoraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Operadora.this,RecargaPagamento.class);
                startActivity(intent);
            }
        });
    }
}