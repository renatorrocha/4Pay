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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private ListView listadados;
    private String[] itens = {"Nome de Preferência:", "Email:", "Telefone:",
            "Endereço:", "Renda mensal:", "Consultar senha de 4 digítos:",
            "Extrato anual de juros tarifas e impostos:"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        listadados = view.findViewById(R.id.listdados);
        //Adaptador para a lista
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        //adiciona adaptador para a lista
        listadados.setAdapter(adaptador);
        //clique na lista
        listadados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valorSelecionado = listadados.getItemAtPosition(position).toString();
                Toast.makeText(view.getContext(), valorSelecionado, Toast.LENGTH_LONG).show();
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

                String[] splitName = user.getNome().trim().split("\\s+");

                String[] itens = {"Nome de Preferência: " + user.getNome(),
                        "Email: " + user.getEmail(),
                        "Telefone:",
                        "Endereço:",
                        "Renda mensal: " + getString(R.string.txt_valor_deposito, GetMask.getValor(user.getSaldo())),
                        "Consultar senha de 4 digítos:",
                        "Extrato anual de juros tarifas e impostos:"};

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getView().getContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        itens
                );
                listadados.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}