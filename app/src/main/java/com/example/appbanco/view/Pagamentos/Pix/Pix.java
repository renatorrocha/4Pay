package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.CriaChavePix;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PixCobrar;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransf;

public class Pix extends AppCompatActivity {

    private TextView tvEnviarPix, tvDepositarPix;
    private ConstraintLayout confPix;
    private ImageView ivArrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);



        tvEnviarPix = findViewById(R.id.tvEnviarPix);
        tvDepositarPix = findViewById(R.id.tvDepositarPix);
        confPix = findViewById(R.id.confPix);
        ivArrowBack = findViewById(R.id.ivArrowBack);

        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });


        tvEnviarPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itPix = getIntent();
                String userSaldo = itPix.getStringExtra("userSaldo");

                Intent intent = new Intent(Pix.this, PixTransf.class);
                intent.putExtra("userSaldo", userSaldo);
                startActivity(intent);
            }
        });

        tvDepositarPix.setOnClickListener(view -> {
            startActivity(new Intent(Pix.this, PixCobrar.class));
        });

        confPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pix.this, TipoChave.class));
            }
        });
    }
}