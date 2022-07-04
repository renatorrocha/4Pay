package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentConfigsBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Dados_Usuario.AtualizarDadosActivity;
import com.example.appbanco.view.Home.Notificacoes;
import com.example.appbanco.view.Home.Sobre;
import com.example.appbanco.view.Login_Cadastro.Login;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
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

        userViewModel.verifyAutenticado();
        userViewModel.getUserData();

        binding.clSair.setOnClickListener(view1 -> {
            FirebaseHelper.getAuth().signOut();
            startActivity(new Intent(view.getContext(), Login.class));
        });

        binding.tvEditarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AtualizarDadosActivity.class));
            }
        });

        binding.cvNoti.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Notificacoes.class));
        });

        binding.clSobre.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), Sobre.class));

        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        getUserData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userViewModel.getUser.observe(getViewLifecycleOwner(), sucess -> {
            if(sucess != null){
                usuario = userViewModel.getUser();
                binding.tvNomeUser.setText(usuario.getNome());


            }else{
                binding.tvNomeUser.setText("live data n ta funcionando");
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
                Usuario user = snapshot.getValue(Usuario.class);
                if(user.getUrlImagem() != null){
                    Picasso.get().load(user.getUrlImagem())
                            .into(binding.ivUserFoto);
                }

                binding.tvNomeUser.setText(user.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}