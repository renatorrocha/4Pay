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
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentProfileBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Dados_Usuario.MeusDadosActivity;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.ChaveCriada;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.clMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MeusDadosActivity.class));
            }
        });

        binding.clMeusCartoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Cartoes.class));
            }
        });

        binding.clMinhasChaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TipoChave.class));
            }
        });

        binding.clMeusSeguros.setOnClickListener(v -> {

        });

        //binding.iv

        return view;
    }

}