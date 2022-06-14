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

        binding.ivCartaoCredito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);

        });

        binding.frameCartao.setOnClickListener(view1 -> {
            if(binding.frameCartao.getVisibility() == View.VISIBLE){
                binding.frameCartao.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameCartao,fragment);
        transaction.commit();
    }
}