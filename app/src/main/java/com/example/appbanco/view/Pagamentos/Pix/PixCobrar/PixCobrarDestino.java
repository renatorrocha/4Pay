package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixCobrarDestinoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.Cobranca;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PixCobrarDestino extends AppCompatActivity {

    ActivityPixCobrarDestinoBinding binding;
    List<String> usuarioList = new ArrayList<>();
    private String pesquisa = "";
    private Cobranca cobranca;
    private String userDestino;
    boolean userEncontrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixCobrarDestinoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAllUsersData();

        cobranca = (Cobranca) getIntent().getSerializableExtra("cobranca");
        binding.tvValorPix.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cobranca.getValor())));


        binding.btnPixFinal.setOnClickListener(view -> {
            configPesquisa();
        });
    }


    private void getAllUsersData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usuarioList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Usuario usuario = ds.getValue(Usuario.class);
                        if (usuario != null) {
                            if (!usuario.getId().equals(FirebaseHelper.getIdFirebase())) {
                                usuarioList.add(usuario.getId());
                            }
                        } else {
                            Toast.makeText(PixCobrarDestino.this, "Nenhum usuario cadastrado.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void configPesquisa() {
        String pesquisa = binding.etPessoaDestino.getText().toString();

        if (!pesquisa.equals("")) {
            getAllChavesPix(pesquisa);
        } else {
            Toast.makeText(this, "Insira os dados corretamente.", Toast.LENGTH_SHORT).show();
        }
    }


    private void getAllChavesPix(String pesquisa) {

        for (int i = 0; i < usuarioList.size(); i++) {
            DatabaseReference chavesPixRef = FirebaseHelper.getDatabaseReference()
                    .child("chavesPix")
                    .child(usuarioList.get(i));
            int finalI = i;

            chavesPixRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ChavePix chavePixAll = ds.getValue(ChavePix.class);

                            if (chavePixAll != null) {
                                if (pesquisa.equals(chavePixAll.getChave())) {
                                    userEncontrado = true;
                                    userDestino = chavePixAll.getIdUsuario();
                                }
                            }

                        }

                        if (userEncontrado) {
                            Intent intent = new Intent(PixCobrarDestino.this, PixCobrarFinal.class);
                            cobranca.setIdDestinatario(userDestino);
                            intent.putExtra("userDestinatario", userDestino);
                            intent.putExtra("cobranca", cobranca);
                            startActivity(intent);


                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(PixTransfDestino.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}