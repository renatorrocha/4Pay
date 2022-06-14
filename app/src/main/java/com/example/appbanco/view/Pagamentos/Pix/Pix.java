package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.CriaChavePix;

public class Pix extends AppCompatActivity {

    private TextView tvEnviarPix;
    private TextView tvDepositarPix;
    private ConstraintLayout confPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);

        tvEnviarPix.findViewById(R.id.tvEnviarPix);
        tvEnviarPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pix.this, PixTransf.class));
            }
        });

        confPix.findViewById(R.id.confPix);
        confPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pix.this, CriaChavePix.class));
            }
        });
    }
}