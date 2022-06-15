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

public class Pix extends AppCompatActivity {

    private TextView tvEnviarPix;
    private ConstraintLayout confPix;
    private ImageView ivArrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);




        tvEnviarPix = findViewById(R.id.tvEnviarPix);
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

        confPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pix.this, CriaChavePix.class));
            }
        });
    }
}