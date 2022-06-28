package com.example.appbanco.view.Dados_Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MeusDadosActivity extends AppCompatActivity {

    private ImageView ivArrowBack;
    private ImageView ivEditar;
    private TextView tvNomeUser;
    private TextView tvEmail;
    private TextView tvTelefone;
    private TextView tvEndereco;
    private TextView tvRendaMensal;
    private ImageView ivUserFoto;


    private Usuario usuario;
    private Endereco enderecos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

        iniciaComponentes();
        getUserData();
        getUserAdress();
    }

    private void iniciaComponentes() {

        ivArrowBack = findViewById(R.id.ivArrowBack);
        ivEditar = findViewById(R.id.ivEditar);
        tvEmail = findViewById(R.id.tvEmail);
        tvNomeUser = findViewById(R.id.tvNomeUser);
        tvTelefone = findViewById(R.id.tvTelefone);
        tvEndereco = findViewById(R.id.tvEndereco);
        ivUserFoto = findViewById(R.id.ivUserFoto);
        tvRendaMensal = findViewById(R.id.tvRendaMensal);

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

    private void configDados(Usuario usuario) {
        tvEmail.setText(usuario.getEmail());
        tvNomeUser.setText(usuario.getNome());
        tvTelefone.setText(usuario.getCelular());
        tvRendaMensal.setText(usuario.getRendimento());

        if (usuario.getUrlImagem() != null) {
            Picasso.get().load(usuario.getUrlImagem())
                    .into(ivUserFoto);
        }
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 usuario = snapshot.getValue(Usuario.class);
                configDados(usuario);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserAdress() {
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        enderecos = dataSnapshot.getValue(Endereco.class);
                    }
                }

                tvEndereco.setText(enderecos.getLogradouro());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
