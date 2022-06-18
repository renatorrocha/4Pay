package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentHomeBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Notificacoes;
import com.example.appbanco.view.Home.Seguros;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Deposito.DepositofFormActivity;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Recarga.Recarga;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Notificacao> notiList = new ArrayList<>();
    FragmentHomeBinding binding;
    private double userSaldo;

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

        binding.clSeguro.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Seguros.class));
        });

        binding.clPix.setOnClickListener(view1 -> {
            Intent itPix = new Intent(getContext(), Pix.class);
            itPix.putExtra("userSaldo", binding.tvSaldoValor.getText().toString());
            startActivity(itPix);
        });

        binding.ivNoti.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Notificacoes.class));
        });


        binding.ivEsconderSaldo.setOnClickListener(view1 -> {
            if (binding.ivEsconderSaldo.getDrawable().getConstantState() == ContextCompat.getDrawable(getContext(), R.drawable.ic_eye).getConstantState()) {
                binding.ivEsconderSaldo.setImageResource(R.drawable.ic_eye_closed);
                binding.tvSaldoValor.setText("***,**");
                binding.tvNumCartao.setText("**** **** **** ****");
                binding.tvValidadeCartao.setText("**/**");
            } else {
                binding.ivEsconderSaldo.setImageResource(R.drawable.ic_eye);
                binding.tvSaldoValor.setText(getString(R.string.txt_valor_saldo, GetMask.getValor(userSaldo)));
                binding.tvNumCartao.setText(R.string.txt_codigo_cartao);
                binding.tvValidadeCartao.setText(R.string.txt_cartao_validade);
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();
        getUserData();
        getAllNoti();
    }


    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                userSaldo = user.getSaldo();
                binding.tvSaldoValor.setText(getString(R.string.txt_valor_saldo, GetMask.getValor(userSaldo)));
                String[] splitName = user.getNome().trim().split("\\s+");
                binding.tvBemVindo.setText("Olá, " + splitName[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllNoti() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(FirebaseHelper.getIdFirebase());
        notiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notiList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Notificacao noti = ds.getValue(Notificacao.class);
                    notiList.add(noti);
                }
                if (notiList.isEmpty()) {
                    binding.tvNoti.setText("0");
                    binding.tvNoti.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvNoti.setText(String.valueOf(notiList.size()));
                    binding.tvNoti.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}