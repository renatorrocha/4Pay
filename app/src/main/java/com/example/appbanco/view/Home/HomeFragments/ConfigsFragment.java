package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbanco.databinding.FragmentConfigsBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Dados_Usuario.AtualizarDadosActivity;
import com.example.appbanco.view.Home.Notificacoes;
import com.example.appbanco.view.Home.Sobre;
import com.example.appbanco.view.Login_Cadastro.Login;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.squareup.picasso.Picasso;

public class ConfigsFragment extends Fragment {

    FragmentConfigsBinding binding;
    private Usuario usuario;
    private GetUserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userViewModel = new ViewModelProvider(getActivity()).get(GetUserViewModel.class);

        userViewModel.verifyUserData();
        userViewModel.getUser.observe(getViewLifecycleOwner(), sucess -> {
            if (sucess != null) {
                usuario = userViewModel.getUser();
                binding.tvNomeUser.setText(usuario.getNome());
                if (usuario.getUrlImagem() != null) {
                    Picasso.get().load(usuario.getUrlImagem())
                            .into(binding.ivUserFoto);
                }
            }
        });

        binding.clSair.setOnClickListener(view1 -> {
            FirebaseHelper.getAuth().signOut();
            startActivity(new Intent(view.getContext(), Login.class));
        });

        binding.tvEditarDados.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), AtualizarDadosActivity.class));
        });

        binding.cvNoti.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Notificacoes.class));
        });

        binding.clSobre.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), Sobre.class));
        });

        return view;
    }


}