package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaBinding;
import com.example.appbanco.view.Home.Home;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

public class Recarga extends AppCompatActivity {

    private ActivityRecargaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        binding = ActivityRecargaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtTelefone.addTextChangedListener(CustomMask.Companion.mask("(##) #####-####", binding.edtTelefone, null));

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recarga.this, RecargaOperadora.class);
                startActivity(intent);
            }
        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });
    }
}