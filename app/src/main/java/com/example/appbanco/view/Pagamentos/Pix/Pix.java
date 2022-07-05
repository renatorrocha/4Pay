package com.example.appbanco.view.Pagamentos.Pix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.databinding.ActivityPixBinding;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PixCobrar;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransf;
import com.example.appbanco.view.Pagamentos.Pix.QRCode.CriarPixQrCode;
import com.example.appbanco.view.Pagamentos.Pix.QRCode.PixQrCode;

public class Pix extends AppCompatActivity {

    private ActivityPixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });


        binding.clEnviarPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itPix = getIntent();
                String userSaldo = itPix.getStringExtra("userSaldo");

                Intent intent = new Intent(Pix.this, PixTransf.class);
                intent.putExtra("userSaldo", userSaldo);
                startActivity(intent);
            }
        });

        binding.clCobrarPix.setOnClickListener(view -> {
            startActivity(new Intent(Pix.this, PixCobrar.class));
        });

        binding.confPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pix.this, TipoChave.class));
            }
        });

        binding.clLerQrCode.setOnClickListener(v -> {
            startActivity(new Intent(Pix.this, PixQrCode.class));
        });

        binding.clCriarQrCode.setOnClickListener(v -> {
            startActivity(new Intent(Pix.this, CriarPixQrCode.class));

        });

    }


}