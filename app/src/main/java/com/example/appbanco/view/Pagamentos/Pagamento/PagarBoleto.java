package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagarBoletoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Boleto;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.RegistrarChavePix;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.TipoChave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class PagarBoleto extends AppCompatActivity {

    private List<Cartao> cartaoList = new ArrayList<>();
    private AlertDialog dialog;
    private ActivityPagarBoletoBinding binding;
    private Boleto boleto = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarBoletoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recuperarCartoes();


        binding.btnProximo.setOnClickListener(v -> {
            showDialogTipoPagamento();
        });
    }

    private void showDialogTipoPagamento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pagamento, null);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        Button btnPagarSaldo = view.findViewById(R.id.btnPagarSaldo);
        Button btnPagarCartao = view.findViewById(R.id.btnPagarCartao);

        ivClose.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        btnPagarSaldo.setOnClickListener(v -> {
            verifyBoleto();
            if (boleto != null) {
                Intent intent = new Intent(PagarBoleto.this, ConfirmarPagamento.class);
                intent.putExtra("tipoPagamento", "saldo");
                intent.putExtra("boleto", boleto);
                startActivity(intent);
            }
        });

        btnPagarCartao.setOnClickListener(v -> {
            dialog.dismiss();
            showDialogTipoCartao();
        });


        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void showDialogTipoCartao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_escolhercartao, null);
        ConstraintLayout clMain = view.findViewById(R.id.clMain);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        Button btnCartaoUm = view.findViewById(R.id.btnCartaoUm);
        Button btnCartaoDois = view.findViewById(R.id.btnCartaoDois);
        Button btnCartaoTres = view.findViewById(R.id.btnCartaoTres);

        ivClose.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        if (cartaoList.size() < 1) {
            btnCartaoUm.setText("Fechar");

            btnCartaoUm.setOnClickListener(v -> {
                dialog.dismiss();
            });

            Toast.makeText(this, "Nenhum cartão encontrado.", Toast.LENGTH_LONG).show();
            clMain.removeView(btnCartaoDois);
            clMain.removeView(btnCartaoTres);


        } else {

            verifyBoleto();
            if (boleto != null) {
                Intent intentNext = new Intent(PagarBoleto.this, ConfirmarPagamento.class);
                intentNext.putExtra("tipoPagamento", "cartao");
                intentNext.putExtra("boleto", boleto);

                btnCartaoUm.setText(cartaoList.get(0).getTipo());
                btnCartaoUm.setOnClickListener(v -> {
                    intentNext.putExtra("tipoCartao", cartaoList.get(0));
                    startActivity(intentNext);
                    dialog.dismiss();
                });


                if (cartaoList.size() == 1) {
                    clMain.removeView(btnCartaoDois);
                    clMain.removeView(btnCartaoTres);
                }

                if (cartaoList.size() == 2) {
                    btnCartaoDois.setText(cartaoList.get(1).getTipo());
                    btnCartaoDois.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(1));
                        startActivity(intentNext);
                        dialog.dismiss();

                    });

                    clMain.removeView(btnCartaoTres);
                }

                if (cartaoList.size() == 3) {
                    btnCartaoDois.setText(cartaoList.get(1).getTipo());
                    btnCartaoDois.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(1));
                        startActivity(intentNext);
                    });

                    btnCartaoTres.setText(cartaoList.get(2).getTipo());
                    btnCartaoTres.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(2));
                        startActivity(intentNext);
                        dialog.dismiss();

                    });
                }


            }
        }

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

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

    private void verifyBoleto() {
        String codigo = binding.etCodigo.getText().toString();
        Boolean verify = true;

        List<Boleto> listBoletos = new ArrayList<>();
        listBoletos.add(new Boleto("Vivo", 52.60, "34191790010104351004791020150008190290005260", "VIVO CELULAR - Controle"));
        listBoletos.add(new Boleto("Kabum", 149.85, "34191790010104351004791020150008190290014985", "Mouse Gamer Logitech G403"));
        listBoletos.add(new Boleto("Netflix", 55.90, "34191790010104351004791020150008190290005590", "Netflix plano Premium"));
        listBoletos.add(new Boleto("Spotify", 19.99, "34191790010104351004791020150008190290001999", "Spotify individual"));
        listBoletos.add(new Boleto("Amazon", 28.90, "34191790010104351004791020150008190290002890", "Suporte notebook"));
        listBoletos.add(new Boleto("Amazon", 1422.50, "34191790010104351004791020150008190290142250", "Monitor AOC 240hz"));

        if (!codigo.equals("")) {
            if (codigo.length() == 44) {
                for (int i = 0; i < listBoletos.size(); i++) {
                    if (codigo.equals(listBoletos.get(i).getCodigo())) {
                        verify = true;
                        boleto = listBoletos.get(i);
                    }
                }

                if (!verify) {
                    Toast.makeText(this, "Codigo inválido", Toast.LENGTH_SHORT).show();
                }


            } else {
                binding.etCodigo.requestFocus();
                binding.etCodigo.setError("Insira um boleto válido");
            }

        } else {
            binding.etCodigo.requestFocus();
            binding.etCodigo.setError("Insira um boleto válido");
        }

    }

}