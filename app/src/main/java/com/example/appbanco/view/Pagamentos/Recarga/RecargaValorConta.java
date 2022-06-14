package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaValorContaBinding;
import com.example.appbanco.view.Home.Home;

public class RecargaValorConta extends AppCompatActivity {

    private ActivityRecargaValorContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_valor_conta);

        binding = ActivityRecargaValorContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSeusSeguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaValorConta.this, Home.class);
                startActivity(intent);
                Toast.makeText(RecargaValorConta.this, "Sua Recarga foi efetuada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}