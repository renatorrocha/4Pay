package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentHomeBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Deposito.DepositofFormActivity;
import com.example.appbanco.view.Pagamentos.Pix.PixTransf;
import com.example.appbanco.view.Pagamentos.Recarga.Recarga;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {


    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.clCartao.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Cartoes.class));
        });

        binding.clDeposito.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), DepositofFormActivity.class));
        });

        binding.clRecarga.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Recarga.class));
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getUserData();

        binding.clPix.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), PixTransf.class);
            intent.putExtra("userSaldo", binding.tvSaldoValor.getText().toString());
            startActivity(intent);
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

                binding.tvSaldoValor.setText(getString(R.string.txt_valor_saldo, GetMask.getValor(user.getSaldo())));
                String[] splitName = user.getNome().trim().split("\\s+");
                binding.tvBemVindo.setText("Bem vindo, " + splitName[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}