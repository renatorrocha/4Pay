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




    }

    @Override
    protected void onStart() {
        super.onStart();

        getAllUsersData();
        configPesquisa();

        binding.btnPixFinal.setOnClickListener(view -> {
            Intent intent = new Intent(this, PixTransfFinal.class);
            intent.putExtra("userDestino", userDestino);

            transf = (Transferencia) getIntent().getSerializableExtra("transferencia");
            intent.putExtra("transferencia", transf);
            startActivity(intent);
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
            pesquisarUsuarios();

        } else {
            Toast.makeText(this, "Insira os dados corretamente.", Toast.LENGTH_SHORT).show();
        }

    }

    private void pesquisarUsuarios() {
        for (Usuario usuario : new ArrayList<>(usuarioList)) {
            if (!usuario.getNome().toLowerCase().contains(pesquisa.toLowerCase())) {
                usuarioList.remove(usuario);
            }
        }

        if (usuarioList.isEmpty()) {
            Toast.makeText(this, "Nenhum usuario com este nome.", Toast.LENGTH_SHORT).show();
        }

        if (usuarioList.size() == 1) {
            userDestino = usuarioList.get(0);
        }

    }

//    private void configFiltro(){
//        if(!pesquisa.equals("")){
//            binding.etPessoaDestino.setText("Pesquisa: " + pesquisa);
//        }else {
//            binding.etPessoaDestino.setText("");
//        }
//    }
}