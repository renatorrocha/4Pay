package com.example.appbanco.view.Dados_Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private EditText edtLogradouroAtt;
    private ProgressBar progressBar;
    private Usuario usuario;
    private Button buttonSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);

        iniciaComponetes();
        getUserData();
        configClicks();
    }

    public void validaDados(View view){

        String nome = edtNomeAtt.getText().toString();
        String email = edtEmailAtt.getText().toString();
        String telefone = edtNumeroAtt.getText().toString();
        String endereco = edtLogradouroAtt.getText().toString();

        if(!nome.isEmpty()){
            if(!telefone.isEmpty()){

                ocultarTeclado();

                progressBar.setVisibility(View.GONE);

                usuario.setNome(nome);
                //usuario.setNumero(numero);

                salvarDadosUser();

                /*if(!endereco.isEmpty()){


                }else{
                    edtLogradouroAtt.requestFocus();
                    edtLogradouroAtt.setError("Informe seu endereço");
                }*/
            }else{
                edtNumeroAtt.requestFocus();
                edtNumeroAtt.setError("Informe seu número");
            }

        }else{
            edtNomeAtt.requestFocus();
            edtNomeAtt.setError("Informe seu nome");
        }

    }

    private void salvarDadosUser() {
        DatabaseReference usuarioRef =FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Informações salvas com sucesso.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Não foi possível salvar as informações.",
                        Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });

    }

    private void configDados(Usuario usuario) {
        edtEmailAtt.setText(usuario.getEmail());
        edtNomeAtt.setText(usuario.getNome());
        //edtLogradouroAtt.setText(usuario.getEndereco().getLogradouro());

        progressBar.setVisibility((View.GONE));
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

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
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
                usuario = snapshot.getValue(Usuario.class);
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
        progressBar = findViewById(R.id.progressBar);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        edtLogradouroAtt = findViewById(R.id.edtlogradouroAtt);

    }

    //Oculta o teclado do dispositivo
    private void ocultarTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtNomeAtt.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}