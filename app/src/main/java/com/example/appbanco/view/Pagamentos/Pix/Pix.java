package com.example.appbanco.view.Pagamentos.Pix;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PixCobrar;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransf;
import com.example.appbanco.view.Pagamentos.Pix.QRCode.CriarPixQrCode;
import com.example.appbanco.view.Pagamentos.Pix.QRCode.PixQrCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Pix extends AppCompatActivity {

    private TextView tvEnviarPix, tvDepositarPix, tvLerQrCode, tvEnviarQrCode;
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
        tvLerQrCode = findViewById(R.id.tvLerQrCode);
        tvEnviarQrCode = findViewById(R.id.tvEnviarQrCode);

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

        tvLerQrCode.setOnClickListener(v -> {
            startActivity(new Intent(Pix.this, PixQrCode.class));
        });

        tvEnviarQrCode.setOnClickListener(v -> {
            startActivity(new Intent(Pix.this, CriarPixQrCode.class));

        });

    }




}