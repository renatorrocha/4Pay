package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagarCobrancaBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cobranca;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Pagamento;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class PagarCobranca extends AppCompatActivity {

    ActivityPagarCobrancaBinding binding;
    private Cobranca cobranca;
    private Notificacao notificacao;
    private Usuario userDestino, userOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarCobrancaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserData();
        notificacao = (Notificacao) getIntent().getSerializableExtra("notificacao");
        if (notificacao != null) {
            recuperaCobranca();
        } else {
            cobranca = (Cobranca) getIntent().getSerializableExtra("cobranca");
            cobranca.setIdDestinatario(FirebaseHelper.getIdFirebase());
            getUserDestinoData();
        }


        binding.btnProximo.setOnClickListener(view1 -> {
            if (notificacao != null) {
                confirmarPagamento();

            } else {
                confirmarPagamentoQrCode();
            }
        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            finish();
        });

    }

    private void confirmarPagamento() {
        if (cobranca != null) {
            if (!cobranca.isPaga()) {
                if (userDestino != null && userOrigem != null) {
                    if (userOrigem.getSaldo() >= cobranca.getValor()) {

                        userOrigem.setSaldo(userOrigem.getSaldo() - cobranca.getValor());
                        userOrigem.atualizarSaldo();

                        userDestino.setSaldo(userDestino.getSaldo() + cobranca.getValor());
                        userDestino.atualizarSaldo();

//                ATUALIZAR COBRANÇA PARA PAGA
                        DatabaseReference cobrancaRef = FirebaseHelper.getDatabaseReference()
                                .child("cobrancas")
                                .child(FirebaseHelper.getIdFirebase())
                                .child("paga");
                        cobrancaRef.setValue(true);

                        salvarExtrato(userOrigem, "SAIDA");
                        salvarExtrato(userDestino, "ENTRADA");


                    } else {
                        Toast.makeText(this, "Sem saldo suficiente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Ainda estamos confirmando o pagamento.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Esta cobrança ja foi paga.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Ainda estamos confirmando o pagamento.", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmarPagamentoQrCode() {
        if (cobranca != null) {
            if (!cobranca.isPaga()) {
                if (userDestino != null && userOrigem != null) {
                    if (userOrigem.getSaldo() >= cobranca.getValor()) {

                        userOrigem.setSaldo(userOrigem.getSaldo() - cobranca.getValor());
                        userOrigem.atualizarSaldo();

                        userDestino.setSaldo(userDestino.getSaldo() + cobranca.getValor());
                        userDestino.atualizarSaldo();

//                ATUALIZAR COBRANÇA PARA PAGA
                        DatabaseReference cobrancaQrCodeRef = FirebaseHelper.getDatabaseReference()
                                .child("cobrancasQrCode")
                                .child(cobranca.getId())
                                .child("paga");
                        cobrancaQrCodeRef.setValue(true);

                        salvarExtrato(userOrigem, "SAIDA");
                        salvarExtrato(userDestino, "ENTRADA");


                    } else {
                        Toast.makeText(this, "Sem saldo suficiente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Ainda estamos confirmando o pagamento.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Esta cobrança ja foi paga.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Ainda estamos confirmando o pagamento.", Toast.LENGTH_SHORT).show();
        }
    }


    private void salvarExtrato(Usuario usuario, String tipo) {

        ExtratoModel extrato = new ExtratoModel();
        extrato.setOperacao("PAGAMENTO");
        extrato.setValor(cobranca.getValor());
        extrato.setTipo(tipo);

        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(usuario.getId())
                .child(extrato.getId());
        extratoRef.setValue(extrato).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DatabaseReference updateExtrato = extratoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

                salvarPagamento(extrato);

            } else {
                Toast.makeText(this, "Não foi possivel realizar o pagamento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarPagamento(ExtratoModel extrato) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(extrato.getId());
        pagamento.setIdCobranca(cobranca.getId());
        pagamento.setValor(cobranca.getValor());
        pagamento.setIdUserOrigem(userOrigem.getId());
        pagamento.setIdUserDestino(userDestino.getId());

        DatabaseReference pagamentoRef = FirebaseHelper.getDatabaseReference()
                .child("pagamentos")
                .child(extrato.getId());
        pagamentoRef.setValue(pagamento).addOnSuccessListener(a -> {
            DatabaseReference update = pagamentoRef.child("data");
            update.setValue(ServerValue.TIMESTAMP);
        });

        if (extrato.getTipo().equals("ENTRADA")) {
            enviaNoti(extrato.getId());
        } else {
            Intent it = new Intent(this, PagarCobrancaRecibo.class);
            it.putExtra("idPagamento", pagamento.getId());
            startActivity(it);
        }
    }

    ;

    private void configDados() {
        binding.tvValorCobranca.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cobranca.getValor())));
        binding.tvData.setText(GetMask.getDate(cobranca.getData(), 3));
        binding.tvUserDestino.setText("De " + userDestino.getNome());
    }

    private void getUserDestinoData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(cobranca.getIdCobrador());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDestino = snapshot.getValue(Usuario.class);
                configDados();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userOrigem = snapshot.getValue(Usuario.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void enviaNoti(String idOperacao) {
        Notificacao notificacao = new Notificacao();
        notificacao.setOperação("PAGAMENTO");
        notificacao.setIdDestinario(userDestino.getId());
        notificacao.setIdCobrador(userOrigem.getId());
        notificacao.setIdOperacao(idOperacao);
        notificacao.enviar();
    }

    private void recuperaCobranca() {
        DatabaseReference cobrancaRef = FirebaseHelper.getDatabaseReference()
                .child("cobrancas")
                .child(FirebaseHelper.getIdFirebase())
                .child(notificacao.getIdOperacao());

        cobrancaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cobranca = snapshot.getValue(Cobranca.class);

                getUserDestinoData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}