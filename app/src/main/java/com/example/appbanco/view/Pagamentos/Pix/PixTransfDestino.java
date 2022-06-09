package com.example.appbanco.view.Pagamentos.Pix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixTransfDestinoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PixTransfDestino extends AppCompatActivity {

    ActivityPixTransfDestinoBinding binding;
    List<Usuario> usuarioList = new ArrayList<>();
    private String pesquisa = "";
    private Transferencia transf;
    private Usuario userDestino;

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
                                usuarioList.add(usuario);
                            }
                        }
                    }
                } else {
                    Toast.makeText(PixTransfDestino.this, "Nenhum usuario cadastrado.", Toast.LENGTH_SHORT).show();
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
            pesquisarUsuarios(pesquisa);
        } else {
            Toast.makeText(this, "Insira os dados corretamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void pesquisarUsuarios(String pesquisa) {
        for (int i = 0; i < usuarioList.size(); i++) {
            if (pesquisa.equals(usuarioList.get(i).getEmail())) {
                userDestino = usuarioList.get(i);
                Intent intent = new Intent(this, PixTransfFinal.class);

                transf.setIdUserDestino(userDestino.getId());
                intent.putExtra("userDestino", userDestino);
                intent.putExtra("transferencia", transf);
                startActivity(intent);
            }
        }

        if (usuarioList.isEmpty()) {
            Toast.makeText(this, "Nenhum usuario com este nome.", Toast.LENGTH_SHORT).show();

        }
    }

}