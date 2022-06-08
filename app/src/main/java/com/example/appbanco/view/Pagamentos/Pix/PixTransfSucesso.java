package com.example.appbanco.view.Pagamentos.Pix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.appbanco.databinding.ActivityPixTransfSucessoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PixTransfSucesso extends AppCompatActivity {

    private ActivityPixTransfSucessoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfSucessoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PixTransfSucesso.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        recuperarTransferencia();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Transferencia transf = (Transferencia) getIntent().getSerializableExtra("transferencia");
        Usuario userDestino = (Usuario) getIntent().getSerializableExtra("userDestino");
        binding.tvPixData.setText(Long.toString(transf.getData()));
        binding.tvValorPixFinal.setText(Double.toString(transf.getValor()));
        binding.tvPixFinalPessoaPara.setText(userDestino.getNome());
        binding.tvCodigoTransferencia.setText("Código: "+ transf.getId());
    }




    private void recuperarTransferencia() {
        Transferencia transf = (Transferencia) getIntent().getSerializableExtra("transferencia") ;
        String idTransferencia = transf.getId();

        DatabaseReference transferenciaRef = FirebaseHelper.getDatabaseReference()
                .child("transferencia")
                .child(idTransferencia);
        transferenciaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Transferencia transferencia = snapshot.getValue(Transferencia.class);
                if(transferencia != null){
                    recuperarUserDestino(transferencia);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recuperarUserDestino(Transferencia transferencia){
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(transferencia.getIdUserDestino());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario userDestino = snapshot.getValue(Usuario.class);
                if(userDestino != null){
                    configDados(transferencia, userDestino);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void configDados(Transferencia transferencia, Usuario userDestino) {
        binding.tvPixData.setText(Long.toString(transferencia.getData()));
        binding.tvValorPixFinal.setText(Double.toString(transferencia.getValor()));
        binding.tvPixFinalPessoaPara.setText(userDestino.getNome());
        binding.tvCodigoTransferencia.setText(transferencia.getId());
    }
}