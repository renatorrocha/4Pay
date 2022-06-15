package com.example.appbanco.view.Login_Cadastro;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

public class Cadastro extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtNascimento;
    private EditText edtemail;
    private EditText edtsenha;
    private EditText edtConfirmarsenha;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        iniciaComponentes();

        edtCpf.addTextChangedListener(CustomMask.Companion.mask("###.###.###-##", edtCpf, null));
        edtNascimento.addTextChangedListener(CustomMask.Companion.mask("##/##/####", edtNascimento, null));

        // Animacao de background
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

    }

    public void  validaDados(View view) {

        String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String dtnascimento = edtNascimento.getText().toString();
        String email = edtemail.getText().toString();
        String senha = edtsenha.getText().toString();
        String confirmasenha = edtConfirmarsenha.getText().toString();

        if (!nome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (!dtnascimento.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!senha.isEmpty()) {
                            if (!confirmasenha.isEmpty()) {
                                if (senha.equals(confirmasenha)) {

                                    progressbar.setVisibility(View.VISIBLE);

                                    Usuario usuario = new Usuario();
                                    usuario.setNome(nome);
                                    usuario.setCpf(cpf);
                                    usuario.setDtanascimento(dtnascimento);
                                    usuario.setEmail(email);
                                    usuario.setSenha(senha);
                                    usuario.setSaldo(0);

                                    cadastrarUsuario(usuario);

                                } else {
                                    edtsenha.setError("Senhas diferentes.");
                                    edtConfirmarsenha.setError("Senhas diferentes.");
                                }
                            } else {
                                edtConfirmarsenha.requestFocus();
                                edtConfirmarsenha.setError("Confirme sua senha.");
                            }
                        } else {
                            edtsenha.requestFocus();
                            edtsenha.setError("Informe sua senha.");
                        }
                    } else {
                        edtemail.requestFocus();
                        edtemail.setError("Informe seu email.");
                    }
                } else {
                    edtNascimento.requestFocus();
                    edtNascimento.setError("Informe sua data de nascimento.");
                }
            } else {
                edtCpf.requestFocus();
                edtCpf.setError("Informe seu cpf.");
            }
        } else {
            edtNome.requestFocus();
            edtNome.setError("Informe seu nome.");
        }

    }

    private void cadastrarUsuario(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                String id = task.getResult().getUser().getUid();
                usuario.setId(id);

                salvarDadosUsuario(usuario);

            }else {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarDadosUsuario(Usuario usuario){
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, CadastroProximo.class));
            }else {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniciaComponentes(){

        edtNome = findViewById(R.id.nome);
        edtCpf=findViewById(R.id.cpf);
        edtNascimento = findViewById(R.id.nascimento);
        edtemail = findViewById(R.id.email);
        edtsenha = findViewById(R.id.senha);
        edtConfirmarsenha=findViewById(R.id.confirmasenha);
        progressbar = findViewById(R.id.progressbar);
    }

}

