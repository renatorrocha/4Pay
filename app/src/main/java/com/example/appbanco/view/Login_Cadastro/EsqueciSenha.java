package com.example.appbanco.view.Login_Cadastro;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.databinding.ActivityEsqueciSenhaBinding;
import com.example.appbanco.help.FirebaseHelper;

public class EsqueciSenha extends AppCompatActivity {

    private ActivityEsqueciSenhaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEsqueciSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEnviar.setOnClickListener(view -> {
            recuperarSenha();
        });

        binding.tvEnviarDenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarSenha();
                Toast.makeText(EsqueciSenha.this, "Enviamos outro email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperarSenha() {
        String email = binding.etEmailEsqSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            enviarEmail(email);
        } else {
            binding.etEmailEsqSenha.requestFocus();
            binding.etEmailEsqSenha.setError("Informe o seu email.");
        }
    }

    private void enviarEmail(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Email enviado com suceso.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            binding.progressbarEsqSenha.setVisibility(View.GONE);
        });
    }
}