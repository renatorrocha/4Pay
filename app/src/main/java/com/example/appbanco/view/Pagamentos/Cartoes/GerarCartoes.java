package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityGerarCartoesBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.HomeFragments.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GerarCartoes extends AppCompatActivity {

    List<Cartao> cartaoList = new ArrayList<>();
    ActivityGerarCartoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGerarCartoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperarCartoes();

        binding.frameCartao.setOnClickListener(view1 -> {
            if (binding.frameCartao.getVisibility() == View.VISIBLE) {
                binding.frameCartao.setVisibility(View.INVISIBLE);
            }

        });

        binding.ivCartaoCredito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.ivCartaoDebito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.ivCartaoDebitoCredito.setOnClickListener(view1 -> {
            replace(new VisualizarCartoes());
            binding.frameCartao.setVisibility(View.VISIBLE);
        });

        binding.btnCartaoCredito.setOnClickListener(view -> {
            verificarCartoes("CREDITO");
        });

        binding.btnCartaoDebito.setOnClickListener(view -> {
            verificarCartoes( "DEBITO");

        });

        binding.btnCartaoCreditoDebito.setOnClickListener(view -> {
            verificarCartoes( "CREDITOEDEBITO");

        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Cartoes.class));
        });

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_bottom, 0);
        transaction.replace(R.id.frameCartao, fragment);
        transaction.commit();

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verificarCartoes(String tipoCartao) {
        Boolean verify = false;

        for (int i = 0; i < cartaoList.size(); i++) {
            if (cartaoList.get(i).getTipo().equals(tipoCartao)) {
                verify = true;
                Toast.makeText(GerarCartoes.this, "Você ja tem este tipo de cartão.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(!verify){
            Intent it = new Intent(GerarCartoes.this, CartaoCriarSenha.class);
            it.putExtra("tipoCartao", tipoCartao);
            startActivity(it);
        }

        if(cartaoList.size() == 0){
            Intent it = new Intent(GerarCartoes.this, CartaoCriarSenha.class);
            it.putExtra("tipoCartao", tipoCartao);
            startActivity(it);        }
    }

}