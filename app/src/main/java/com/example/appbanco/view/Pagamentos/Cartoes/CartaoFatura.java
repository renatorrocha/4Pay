package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ExtratoAdapter;
import com.example.appbanco.adapter.ExtratoCartaoAdapter;
import com.example.appbanco.databinding.ActivityCartaoFaturaBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.view.Pagamentos.Pagamento.ConfirmarPagamento;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartaoFatura extends AppCompatActivity {

    BottomSheetBehavior bottomSheetBehavior;
    ActivityCartaoFaturaBinding binding;
    private Cartao cartao;
    private ExtratoCartaoAdapter extratoAdapter;
    private List<ExtratoModel> extratoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoFaturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(view -> { finish();});

        setData();

        recuperarExtrato();

        binding.rvExtrato.setLayoutManager(new LinearLayoutManager(this));
        binding.rvExtrato.setHasFixedSize(true);
        extratoAdapter = new ExtratoCartaoAdapter(extratoList, getBaseContext());
        binding.rvExtrato.setAdapter(extratoAdapter);


        ConstraintLayout clBehavior = findViewById(R.id.clBehavior);
        bottomSheetBehavior = BottomSheetBehavior.from(clBehavior);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        binding.btnFatura.setOnClickListener(v -> {
            Intent intent = new Intent(this, ConfirmarPagamento.class);
            intent.putExtra("tipoPagamento", "fatura");
            intent.putExtra("tipoCartao", cartao);
            startActivity(intent);
        });


    }

    private void setData(){
        if(getIntent().hasExtra("cartao")){
            cartao = (Cartao) getIntent().getSerializableExtra("cartao");
            binding.tvTipoCartao.setText(cartao.getTipo());
            binding.tvPin.setText(cartao.getPin());
            binding.tvNumCartao.setText(cartao.getNumeros());
            binding.tvDataCriacao.setText(cartao.getDataCriacao());
            binding.tvDataValidade.setText(cartao.getDataVencimento());
            binding.tvValidadeCartao.setText(cartao.getDataVencimento());
            binding.tvValorFatura.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getSaldo())));
            binding.tvValorDisponivel.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getLimite() - cartao.getSaldo())));
            binding.tvValorLimite.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getLimite())));
        }
    }

    private void recuperarExtrato() {
        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratosCartao")
                .child(FirebaseHelper.getIdFirebase())
                .child(cartao.getTipo());
        extratoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extratoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ExtratoModel extrato = ds.getValue(ExtratoModel.class);
                        extratoList.add(extrato);
                    }

                } else {
                    //Toast.makeText(getView().getContext(), "Nenhum extrato encontrado.", Toast.LENGTH_SHORT).show();
                }

                Collections.reverse(extratoList);

                extratoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}