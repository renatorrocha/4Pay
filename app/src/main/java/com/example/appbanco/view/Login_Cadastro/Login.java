package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.appbanco.databinding.ActivityLoginBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Recarga.RecargaInicio;


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

        AnimationDrawable animationDrawable = (AnimationDrawable) binding.mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


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