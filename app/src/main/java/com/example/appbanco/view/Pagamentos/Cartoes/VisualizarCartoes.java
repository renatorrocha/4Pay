package com.example.appbanco.view.Pagamentos.Cartoes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentVisualizarCartoesBinding;


public class VisualizarCartoes extends Fragment {

    FragmentVisualizarCartoesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVisualizarCartoesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }
}