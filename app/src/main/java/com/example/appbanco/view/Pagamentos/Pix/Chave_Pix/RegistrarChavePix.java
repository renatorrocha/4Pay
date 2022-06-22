package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRegistrarChavePixBinding;

public class RegistrarChavePix extends AppCompatActivity {

    ActivityRegistrarChavePixBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarChavePixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setData();

        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnCriarChave.setOnClickListener(view1 -> {
            startActivity(new Intent(RegistrarChavePix.this, ChaveCriada.class));
        });
    }

    private void setData() {
        String tipoChave = getIntent().getStringExtra("tipoChave");

        binding.tvTitulo.setText(getString(R.string.txt_registrar_chave, tipoChave));
        binding.btnCriarChave.setText(getString(R.string.txt_registrar_chave, tipoChave));
        binding.tvDesc.setText(getString(R.string.txt_desc_tipo_chave, tipoChave));

        binding.tvChave.setText(tipoChave);

        if (tipoChave.equals("email")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_email);

        } else if (tipoChave.equals("celular")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_cel);

        } else if (tipoChave.equals("cpf")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_id);

        } else if (tipoChave.equals("chaveAleatoria")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_sec);
        }

    }
}