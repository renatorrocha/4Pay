package com.example.appbanco.view.Seguros;

import androidx.annotation.NonNull;
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
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.ListaSeguro;
import com.example.appbanco.model.SeguroModel;
import com.example.appbanco.model.SegurosUsuario;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Home.Seguros;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SegurosAtivos extends AppCompatActivity {


    private SegurosAtivosAdapter segurosAdapter;
    private List<SeguroModel> seguroList = new ArrayList<>();
    private RecyclerView rvSeguros;
    private ImageView ivArrowBack;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguros_ativos);

        recuperarSeguros();

        rvSeguros = findViewById(R.id.rvSegurosAtivos);
        rvSeguros.setLayoutManager(new LinearLayoutManager(this));
        segurosAdapter = new SegurosAtivosAdapter(seguroList, getBaseContext());
        rvSeguros.setAdapter(segurosAdapter);

        ivArrowBack = findViewById(R.id.ivArrowBack);
        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Seguros.class));
        });
    }

    private void recuperarSeguros(){

        DatabaseReference segurosRef = FirebaseHelper.getDatabaseReference()
                .child("seguros")
                .child(FirebaseHelper.getIdFirebase());
        segurosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seguroList.clear();
                if(snapshot.exists()){

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        SeguroModel seguro = ds.getValue(SeguroModel.class);
                        seguroList.add(seguro);
                    }

                }
                segurosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}