package com.example.appbanco.view.Pagamentos.Pix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.appbanco.R;

import com.example.appbanco.databinding.ActivityPixTransfFinalBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PixTransfFinal extends AppCompatActivity {

    ActivityPixTransfFinalBinding binding;

    private Transferencia transferencia;
    private Usuario userDestino;
    private Usuario userOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configDados();
        recuperaUsuarioOrigem();

        binding.btnTransfPixFinal.setOnClickListener(view -> {
            confirmarTransf(view);
        });


    }

    public void confirmarTransf(View view) {
        if (transferencia != null) {
            if (userOrigem.getSaldo() >= transferencia.getValor()) {

                userOrigem.setSaldo(userOrigem.getSaldo() - transferencia.getValor());
                userOrigem.atualizarSaldo();

                userDestino.setSaldo(userDestino.getSaldo() + transferencia.getValor());
                userDestino.atualizarSaldo();

                Intent intent = new Intent(this, PixTransfSucesso.class);
                if (transferencia != null) {
                    intent.putExtra("transferencia", transferencia);
                    intent.putExtra("userDestino", userDestino);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Sem saldo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void recuperaUsuarioOrigem() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(transferencia.getIdUserOrigem());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userOrigem = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados() {
        userDestino = (Usuario) getIntent().getSerializableExtra("userDestino");
        transferencia = (Transferencia) getIntent().getSerializableExtra("transferencia");

        binding.tvUserDestino.setText("Para "+ userDestino.getNome());
        binding.tvValorTransfPix.setText("R$ " + String.valueOf(transferencia.getValor()));
        binding.tvDataTransf.setText(Long.toString((transferencia.getData())));

    }
}