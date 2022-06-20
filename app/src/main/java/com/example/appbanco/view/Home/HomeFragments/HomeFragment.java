package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.FragmentHomeBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Notificacoes;
import com.example.appbanco.view.Home.Seguros;
import com.example.appbanco.view.Pagamentos.Cartoes.CartaoCriarSenha;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Cartoes.GerarCartoes;
import com.example.appbanco.view.Pagamentos.Deposito.DepositofFormActivity;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Recarga.RecargaInicio;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Cartao> cartaoList = new ArrayList<>();
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
            startActivity(new Intent(view.getContext(), RecargaInicio.class));
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
                binding.tvSaldoValor.setText("**,**");

                if (cartaoList.size() == 1) {
                    binding.tvNumCartao.setText("**** **** **** ****");
                    binding.tvValidadeCartao.setText("**/**");

                } else if (cartaoList.size() == 2) {
                    binding.tvNumCartao.setText("**** **** **** ****");
                    binding.tvValidadeCartao.setText("**/**");

                    binding.tvNumCartaoDois.setText("**** **** **** ****");
                    binding.tvValidadeCartaoDois.setText("**/**");

                } else if (cartaoList.size() == 3) {
                    binding.tvNumCartao.setText("**** **** **** ****");
                    binding.tvValidadeCartao.setText("**/**");

                    binding.tvNumCartaoDois.setText("**** **** **** ****");
                    binding.tvValidadeCartaoDois.setText("**/**");

                    binding.tvNumCartaoTres.setText("**** **** **** ****");
                    binding.tvValidadeCartaoTres.setText("**/**");

                }

            } else {
                binding.ivEsconderSaldo.setImageResource(R.drawable.ic_eye);
                binding.tvSaldoValor.setText(getString(R.string.txt_valor_saldo, GetMask.getValor(userSaldo)));
                recuperarCartoes();
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();
        getUserData();
        getAllNoti();
        recuperarCartoes();
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

    private void recuperarCartoes() {

        DatabaseReference cartoesRef = FirebaseHelper.getDatabaseReference()
                .child("cartoes")
                .child(FirebaseHelper.getIdFirebase());
        cartoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartaoList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Cartao cartao = ds.getValue(Cartao.class);
                    cartaoList.add(cartao);
                }

                if (cartaoList.size() > 0) {
                    verificarCartoes();
                } else {
                    binding.clCartao.setBackgroundResource(R.drawable.ic_gerar_cartao);
                    binding.tvTipoCartao.setText("");
                    binding.tvValidadeCartao.setText("");
                    binding.tvNumCartao.setText("");

                    binding.clListaCartoes.removeView(binding.clCartaoDois);
                    binding.clListaCartoes.removeView(binding.clCartaoTres);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verificarCartoes() {

        if (cartaoList.size() == 1) {
            binding.clCartao.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.clListaCartoes.removeView(binding.clCartaoDois);
            binding.clListaCartoes.removeView(binding.clCartaoTres);


        } else if (cartaoList.size() == 2) {
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.tvTipoCartaoDois.setText(cartaoList.get(1).getTipo());
            String[] splitNumero2 = cartaoList.get(1).getNumeros().trim().split(" ");
            binding.tvNumCartaoDois.setText(getString(R.string.txt_codigo_cartao_put, splitNumero2[3]));
            binding.tvValidadeCartaoDois.setText(cartaoList.get(1).getDataVencimento());


            binding.clListaCartoes.removeView(binding.clCartaoTres);
        } else if (cartaoList.size() == 3) {
            binding.clCartao.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.clCartaoDois.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartaoDois.setText(cartaoList.get(1).getTipo());
            String[] splitNumero2 = cartaoList.get(1).getNumeros().trim().split(" ");
            binding.tvNumCartaoDois.setText(getString(R.string.txt_codigo_cartao_put, splitNumero2[3]));
            binding.tvValidadeCartaoDois.setText(cartaoList.get(1).getDataVencimento());

            binding.clCartaoTres.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(2).getTipo());
            String[] splitNumero3 = cartaoList.get(2).getNumeros().trim().split(" ");
            binding.tvNumCartaoTres.setText(getString(R.string.txt_codigo_cartao_put, splitNumero3[3]));
            binding.tvValidadeCartaoTres.setText(cartaoList.get(2).getDataVencimento());

        }


    }
}