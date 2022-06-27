package com.example.appbanco.view.Dados_Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appbanco.R;
import com.example.appbanco.view.Home.Home;

public class MeusDadosActivity extends AppCompatActivity {

    private ImageView ivArrowBack;
    private ImageView ivEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

        iniciaComponentes();
    }

    private void iniciaComponentes() {

        ivArrowBack = findViewById(R.id.ivArrowBack);
        ivEditar = findViewById(R.id.ivEditar);

        ivArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeusDadosActivity.this, AtualizarDadosActivity.class));
            }
        });
    }

}
