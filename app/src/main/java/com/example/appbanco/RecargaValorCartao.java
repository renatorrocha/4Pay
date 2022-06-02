package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.databinding.ActivityRecargaBinding;
import com.example.appbanco.databinding.ActivityRecargaValorCartaoBinding;

public class RecargaValorCartao extends AppCompatActivity {

    private ActivityRecargaValorCartaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_valor_cartao);

        binding = ActivityRecargaValorCartaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaValorCartao.this,Home.class);
                startActivity(intent);
                Toast.makeText(RecargaValorCartao.this, "Sua Recarga foi efetuada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}