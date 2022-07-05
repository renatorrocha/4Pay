package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.appbanco.databinding.FragmentProfileBinding;
import com.example.appbanco.view.Dados_Usuario.MeusDadosActivity;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.example.appbanco.view.Seguros.SegurosAtivos;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.clMeusDados.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), MeusDadosActivity.class));
        });

        binding.clMeusCartoes.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Cartoes.class));
        });

        binding.clMinhasChaves.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), TipoChave.class));
        });

        binding.clMeusSeguros.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), SegurosAtivos.class));
        });

        return view;
    }

}