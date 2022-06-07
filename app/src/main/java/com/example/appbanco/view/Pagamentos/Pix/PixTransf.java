package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appbanco.R;

public class PixTransf extends AppCompatActivity {

    Button btnProximo;
    private EditText edtValorPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_transf);

        btnProximo = findViewById(R.id.btnPixProximo);

        btnProximo.setOnClickListener(view -> {
            startActivity(new Intent(this, PixTransfDestino.class));
        });


    }

    /*public void validaDados(View view){
        double valor = (double) edtValorPix.getRawValue / 100;

        if(valor >= 15){

        }else{

        }
    }*/
}