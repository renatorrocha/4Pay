package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.databinding.ActivityLoginBinding;
import com.example.appbanco.view.Chave_Pix.CriaChavePix;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.view.Home.Home;


public class Login extends AppCompatActivity {

    private Button btnEntrar;
    private EditText edtemail;
    private EditText edtSenha;
    private TextView tvCadastrar;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaDados();
            }
        });

        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Cadastro.class));
            }
        });

    }

    public void validaDados() {

        String email = edtemail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                progressbar.setVisibility(View.VISIBLE);
                logar(email, senha);

            } else {
                edtSenha.requestFocus();
                edtSenha.setError("Informe sua senha");
            }

        } else {
            edtemail.requestFocus();
            edtemail.setError("Informe seu email");
        }
    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, Home.class));
            } else {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void iniciaComponentes() {

        edtemail = findViewById(R.id.etEmailLogin);
        edtSenha = findViewById(R.id.etSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        tvCadastrar = findViewById(R.id.tvCadastrar);
        progressbar = findViewById(R.id.progressbar);
    }
}