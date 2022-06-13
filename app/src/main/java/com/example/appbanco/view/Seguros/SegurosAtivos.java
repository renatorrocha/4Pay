package com.example.appbanco.view.Seguros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.adapter.SegurosAtivosAdapter;
import com.example.appbanco.model.ListaSeguro;
import com.example.appbanco.model.SegurosUsuario;
import com.example.appbanco.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SegurosAtivos extends AppCompatActivity {

    List<ListaSeguro> listSeguros = new ArrayList<>();

    private SegurosAtivosAdapter segurosAdapter;
    private RecyclerView rvSeguros;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguros_ativos);
        SegurosUsuario seguros = new SegurosUsuario();
        if (seguros.getSeguroCartao()) {
            listSeguros.add(new ListaSeguro("Seguro de Crédito", "Com o seguro " +
                    "cartao da 4pay, voce pode ficar tranquilo que te protegemos contra alguns " +
                    "imprevistos", "10/25"));
        }
        if (seguros.getSeguroVida()) {
            listSeguros.add(new ListaSeguro("Seguro de Vida", "Seu corpinho " +
                    "ainda vai ser meu >:)", "10/25"));
        }
        rvSeguros = findViewById(R.id.rvSegurosAtivos);
        rvSeguros.setLayoutManager(new LinearLayoutManager(this));
        segurosAdapter = new SegurosAtivosAdapter(listSeguros);
        rvSeguros.setAdapter(segurosAdapter);
    }
}