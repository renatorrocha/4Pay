package com.example.appbanco.view.Seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.appbanco.R;
import com.example.appbanco.adapter.SegurosAtivosAdapter;
import com.example.appbanco.model.ListaSeguro;
import com.example.appbanco.model.SegurosUsuario;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Home.Seguros;

import java.util.ArrayList;
import java.util.List;

public class SegurosAtivos extends AppCompatActivity {

    List<ListaSeguro> listSeguros = new ArrayList<>();

    private SegurosAtivosAdapter segurosAdapter;
    private RecyclerView rvSeguros;
    private ImageView ivArrowBack;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguros_ativos);
        SegurosUsuario seguros = new SegurosUsuario();
        if (seguros.getSeguroCartao()) {
            listSeguros.add(new ListaSeguro("Seguro de Crédito", "Com o seguro " +
                    "cartao da 4pay, você pode ficar tranquilo que te protegemos contra alguns " +
                    "imprevistos", "10/25"));
        }
        if (seguros.getSeguroVida()) {
            listSeguros.add(new ListaSeguro("Seguro de Vida", "Com nosso seguro " +
                    "você e seus familiares podem ficar tranquilos em relacao a despesas hospitalares que oferecemos cobertura para auxiliar e confortar seus agregados..", "10/25"));
        }
        rvSeguros = findViewById(R.id.rvSegurosAtivos);
        rvSeguros.setLayoutManager(new LinearLayoutManager(this));
        segurosAdapter = new SegurosAtivosAdapter(listSeguros);
        rvSeguros.setAdapter(segurosAdapter);

        ivArrowBack = findViewById(R.id.ivArrowBack);
        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Seguros.class));
        });
    }
}