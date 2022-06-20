package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.adapter.Photo;
import com.example.appbanco.adapter.Photoadapter;
import com.example.appbanco.databinding.FragmentConfigsBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Dados_Usuario.AtualizarDadosActivity;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Login_Cadastro.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ConfigsFragment extends Fragment {

    FragmentConfigsBinding binding;
    private TextView tvEditarDados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

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



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getUserData();
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                binding.tvNomeUser.setText(user.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}