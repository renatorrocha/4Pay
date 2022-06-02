package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.databinding.ActivityRecargaValorCartaoBinding;
import com.example.appbanco.databinding.ActivityRecargaValorContaBinding;

public class RecargaValorConta extends AppCompatActivity {

    private ActivityRecargaValorContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_valor_conta);

        binding = ActivityRecargaValorContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaValorConta.this,Home.class);
                startActivity(intent);
                Toast.makeText(RecargaValorConta.this, "Sua Recarga foi efetuada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}