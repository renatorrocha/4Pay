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

    ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        binding.btnEntrar.setOnClickListener(view -> {
            validaDados();
        });

        binding.tvCadastrar.setOnClickListener(view ->  {
            startActivity(new Intent(Login.this, Cadastro.class));
        });

        binding.tvEsqueciSenha.setOnClickListener(view -> {
            startActivity(new Intent(this, EsqueciSenha.class));
        });

    }

    public void validaDados() {
        String email = binding.etEmailLogin.getText().toString();
        String senha = binding.etSenhaLogin.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                binding.progressbar.setVisibility(View.VISIBLE);
                logar(email, senha);

            } else {
                binding.etSenhaLogin.requestFocus();
                binding.etSenhaLogin.setError("Informe sua senha");
            }

        } else {
            binding.etEmailLogin.requestFocus();
            binding.etEmailLogin.setError("Informe seu email");
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
                binding.progressbar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}