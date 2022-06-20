package com.example.appbanco.view.Dados_Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AtualizarDadosActivity extends AppCompatActivity {

    private ImageView ivArrowBack;
    private EditText edtNomeAtt;
    private EditText edtNumeroAtt;
    private EditText edtEmailAtt;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);

        iniciaComponetes();
        getUserData();
        configClicks();
    }

    private void configDados(Usuario usuario) {
        edtEmailAtt.setText(usuario.getEmail());
        edtNomeAtt.setText(usuario.getNome());

        //progressBar.setVisibility((View.GONE));
    }

    private void configClicks() {
        ivArrowBack.setOnClickListener(new
 View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AtualizarDadosActivity.this,
                        MeusDadosActivity.class));
            }
        });

    }
    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Usuario usuario = snapshot.getValue(Usuario.class);
               configDados(usuario);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciaComponetes() {

        ivArrowBack = findViewById(R.id.ivArrowBack);
        edtNomeAtt = findViewById(R.id.edtNomeAtt);
        edtNumeroAtt = findViewById(R.id.edtNumeroAtt);
        edtEmailAtt = findViewById(R.id.edtEmailAtt);
        progressBar = findViewById(R.id.progressbar);

    }
}