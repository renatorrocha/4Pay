package com.example.appbanco.view.Pagamentos.Pix.PixTransferir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.appbanco.R;

import com.example.appbanco.databinding.ActivityPixTransfFinalBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class PixTransfFinal extends AppCompatActivity {

    ActivityPixTransfFinalBinding binding;

    private Transferencia transferencia;
    private Usuario userDestino;
    private Usuario userOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configDados();
        recuperaUsuarioOrigem();

        binding.btnTransfPixFinal.setOnClickListener(view -> {
            confirmarTransf(view);
        });


    }

    public void confirmarTransf(View view) {
        if (transferencia != null) {
            if (userOrigem.getSaldo() >= transferencia.getValor()) {

                userOrigem.setSaldo(userOrigem.getSaldo() - transferencia.getValor());
                userOrigem.atualizarSaldo();

                userDestino.setSaldo(userDestino.getSaldo() + transferencia.getValor());
                userDestino.atualizarSaldo();


                salvarExtrato(userOrigem, "SAIDA");
                salvarExtrato(userDestino, "ENTRADA");
            } else {
                Toast.makeText(this, "Sem saldo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void recuperaUsuarioOrigem() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(transferencia.getIdUserOrigem());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userOrigem = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void salvarExtrato(Usuario usuario, String tipo){

        ExtratoModel extrato =  new ExtratoModel();
        extrato.setOperacao("TRANSFERENCIA");
        extrato.setValor(transferencia.getValor());
        extrato.setTipo(tipo);

        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(usuario.getId())
                .child(extrato.getId());
        extratoRef.setValue(extrato).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                DatabaseReference updateExtrato = extratoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

                salvarTransferencia(extrato);

            }else{
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void salvarTransferencia(ExtratoModel extrato){

        transferencia.setId(extrato.getId());

        DatabaseReference transferenciaRef = FirebaseHelper.getDatabaseReference()
                .child("transferencias")
                .child(transferencia.getId());

        transferenciaRef.setValue(transferencia).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                DatabaseReference updateTransferencia = transferenciaRef
                        .child("data");
                updateTransferencia.setValue(ServerValue.TIMESTAMP);

                if(extrato.getTipo().equals("SAIDA")){

                    enviaNoti(extrato.getId());
                    Intent intent = new Intent(this, PixTransfSucesso.class);
                    intent.putExtra("idTransferencia",transferencia.getId());
                    startActivity(intent);
                }

            }else{
                Toast.makeText(this, "Não foi possivel completar a transferência.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void enviaNoti(String idOperacao){
        Notificacao notificacao = new Notificacao();
        notificacao.setOperação("TRANSFERENCIA");
        notificacao.setIdDestinario(userDestino.getId());
        notificacao.setIdCobrador(userOrigem.getId());
        notificacao.setIdOperacao(idOperacao);
        notificacao.enviar();
    }


    private void configDados() {
        userDestino = (Usuario) getIntent().getSerializableExtra("userDestino");
        transferencia = (Transferencia) getIntent().getSerializableExtra("transferencia");

        binding.tvUserDestino.setText("Para "+ userDestino.getNome());
        binding.tvValorTransfPix.setText(getString( R.string.txt_valor_deposito, GetMask.getValor(transferencia.getValor())));

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formataData.format(System.currentTimeMillis());

        binding.tvData.setText(dataFormatada);


    }
}