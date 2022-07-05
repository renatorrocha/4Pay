package com.example.appbanco.view.Dados_Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityMeusDadosBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MeusDadosActivity extends AppCompatActivity {

    private GetUserViewModel userViewModel;
    private ActivityMeusDadosBinding binding;
    private Usuario usuario;
    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeusDadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(GetUserViewModel.class);
        userViewModel.verifyUserData();
        userViewModel.verifyEndereco();

        userViewModel.getUser.observe(this, sucess -> {
            if (sucess) {
                usuario = userViewModel.getUser();
                configDados(usuario);

            }
        });

        userViewModel.getEndereco.observe(this, sucess -> {
            if(sucess){
                endereco = userViewModel.getEndereco();

                binding.tvEndereco.setText(endereco.getLogradouro());

            }
        });

        iniciaComponentes();
    }

    private void iniciaComponentes() {
        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.ivEditar.setOnClickListener(view -> {
            startActivity(new Intent(MeusDadosActivity.this, AtualizarDadosActivity.class));
        });
    }

    private void configDados(Usuario usuario) {
        binding.tvEmail.setText(usuario.getEmail());
        binding.tvNomeUser.setText(usuario.getNome());
        binding.tvTelefone.setText(usuario.getCelular());
        binding.tvRendaMensal.setText("R$" + usuario.getRendimento());

        if (usuario.getUrlImagem() != null) {
            Picasso.get().load(usuario.getUrlImagem())
                    .into(binding.ivUserFoto);
        }
    }

}
