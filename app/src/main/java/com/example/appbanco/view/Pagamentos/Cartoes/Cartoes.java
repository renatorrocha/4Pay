package com.example.appbanco.view.Pagamentos.Cartoes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityCartoesBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cartoes extends AppCompatActivity {

    List<Cartao> cartaoList = new ArrayList<>();
    private ConstraintLayout gerarCartão;
    ActivityCartoesBinding binding;
    private Cartao cartaoUm, cartaoDois, cartaoTres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperarCartoes();

        binding.clCartao.setOnClickListener(view1 -> {
            if (cartaoList.size() < 1) {
                startActivity(new Intent(this, GerarCartoes.class));
            } else {
                Intent it = new Intent(this, CartaoFatura.class);
                it.putExtra("cartao", cartaoUm);
                startActivity(it);
            }
        });

        binding.clCartaoDois.setOnClickListener(view1 -> {
            Intent it = new Intent(this, CartaoFatura.class);
            it.putExtra("cartao", cartaoDois);
            startActivity(it);
        });

        binding.clCartaoTres.setOnClickListener(view1 -> {
            Intent it = new Intent(this, CartaoFatura.class);
            it.putExtra("cartao", cartaoTres);
            startActivity(it);
        });


        binding.clGerarCartao.setOnClickListener(view1 -> {
            Intent it = new Intent(this, GerarCartoes.class);
            startActivity(it);
        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
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
            cartaoUm = cartaoList.get(0);

            binding.clCartao.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.clListaCartoes.removeView(binding.clCartaoDois);
            binding.clListaCartoes.removeView(binding.clCartaoTres);


        } else if (cartaoList.size() == 2) {
            cartaoUm = cartaoList.get(0);
            cartaoDois = cartaoList.get(1);

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
            cartaoUm = cartaoList.get(0);
            cartaoDois = cartaoList.get(1);
            cartaoTres = cartaoList.get(2);

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
            binding.tvTipoCartaoTres.setText(cartaoList.get(2).getTipo());
            String[] splitNumero3 = cartaoList.get(2).getNumeros().trim().split(" ");
            binding.tvNumCartaoTres.setText(getString(R.string.txt_codigo_cartao_put, splitNumero3[3]));
            binding.tvValidadeCartaoTres.setText(cartaoList.get(2).getDataVencimento());

        }


    }
}