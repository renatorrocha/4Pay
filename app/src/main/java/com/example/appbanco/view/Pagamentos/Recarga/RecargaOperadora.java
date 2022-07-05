package com.example.appbanco.view.Pagamentos.Recarga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaOperadoraBinding;

public class RecargaOperadora extends AppCompatActivity {

    private ActivityRecargaOperadoraBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_operadora);

        binding = ActivityRecargaOperadoraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaOperadora.this, RecargaPagamento.class);
                startActivity(intent);
            }
        });
    }

}