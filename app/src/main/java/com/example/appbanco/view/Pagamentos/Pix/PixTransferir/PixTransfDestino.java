package com.example.appbanco.view.Pagamentos.Pix.PixTransferir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixTransfDestinoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.RegistrarChavePix;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PixTransfDestino extends AppCompatActivity {

    ActivityPixTransfDestinoBinding binding;
    List<String> usuarioList = new ArrayList<>();
    private String pesquisa = "";
    private Transferencia transf;
    private String userDestino;
    boolean userEncontrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfDestinoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAllUsersData();

        transf = (Transferencia) getIntent().getSerializableExtra("transferencia");
        binding.tvValorPix.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(transf.getValor())));


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
                            Toast.makeText(PixTransfDestino.this, "Nenhum usuario cadastrado.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                                Intent intent = new Intent(PixTransfDestino.this, PixTransfFinal.class);
                                transf.setIdUserDestino(userDestino);
                                intent.putExtra("userDestino", userDestino);
                                intent.putExtra("transferencia", transf);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PixTransfDestino.this, "Nenhuma conta com esta chave foi encontrada.", Toast.LENGTH_SHORT).show();
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

    private void configPesquisa() {
        String pesquisa = binding.etPessoaDestino.getText().toString();

        if (!pesquisa.equals("")) {
            getAllChavesPix(pesquisa);
        } else {
            Toast.makeText(this, "Insira os dados corretamente.", Toast.LENGTH_SHORT).show();
        }
    }

//    private void pesquisarUsuarios(String pesquisa) {
//
//        for (int i = 0; i < chavesList.size(); i++) {
//            if (pesquisa.equals(chavesList.get(i).getChave())) {
//                userEncontrado = true;
//                userDestino = chavesList.get(i).getIdUsuario();
//                Intent intent = new Intent(this, PixTransfFinal.class);
//
//                transf.setIdUserDestino(userDestino);
//                intent.putExtra("userDestino", userDestino);
//                intent.putExtra("transferencia", transf);
//                startActivity(intent);
//            }
//        }
//
//        if (!userEncontrado) {
//            Toast.makeText(this, "Nenhuma conta com esta chave foi encontrada.", Toast.LENGTH_SHORT).show();
//        }
//    }

}