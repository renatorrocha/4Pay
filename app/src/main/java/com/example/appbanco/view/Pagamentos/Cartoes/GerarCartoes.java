package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityGerarCartoesBinding;
import com.example.appbanco.view.Home.HomeFragments.HomeFragment;

public class GerarCartoes extends AppCompatActivity {

    ActivityGerarCartoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGerarCartoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.frameCartao.setOnClickListener(view1 -> {
            if (binding.frameCartao.getVisibility() == View.VISIBLE) {
                binding.frameCartao.setVisibility(View.INVISIBLE);
            }

        });

        binding.ivCartaoCredito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.ivCartaoDebito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.ivCartaoDebitoCredito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.btnCartaoCredito.setOnClickListener(view -> {
            startActivity(new Intent(this, CartaoCriado.class));
        });

        binding.btnCartaoDebito.setOnClickListener(view -> {
            startActivity(new Intent(this, CartaoCriado.class));
        });

        binding.btnCartaoCreditoDebito.setOnClickListener(view -> {
            startActivity(new Intent(this, CartaoCriado.class));
        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Cartoes.class));
        });

    }

    private void replace(Fragment fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.from_bottom, 0);
            transaction.replace(R.id.frameCartao, fragment);
            transaction.commit();

    }


}