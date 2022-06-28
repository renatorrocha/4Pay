package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityConfirmarPagamentoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Boleto;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Pagamento;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Cartoes.CartaoFatura;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PagarCobrancaRecibo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class ConfirmarPagamento extends AppCompatActivity {

    private ActivityConfirmarPagamentoBinding binding;
    private String tipoPagamento = "";
    private Usuario usuario;
    private Boleto boleto;
    private Cartao cartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmarPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tipoPagamento = getIntent().getStringExtra("tipoPagamento");
        if (tipoPagamento.equals("cartao")) {
            cartao = (Cartao) getIntent().getSerializableExtra("tipoCartao");
        }

        if(tipoPagamento.equals("fatura") || tipoPagamento.equals("pagarFatura")){
            cartao = (Cartao) getIntent().getSerializableExtra("tipoCartao");

            binding.tvValor.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getSaldo())));
            binding.tvParaQuem.setText("Tipo");
            binding.tvPara.setText("Fatura");

            binding.clMain.removeView(binding.tvDataVenc);
            binding.clMain.removeView(binding.tvData);
            binding.clMain.removeView(binding.tvLinha3);
            binding.clMain.removeView(binding.tvCdBarras);
            binding.clMain.removeView(binding.tvCodigo);
            binding.clMain.removeView(binding.tvLinha4);
        }

        setData();
        getUserData();

        binding.btnConfirmar.setOnClickListener(view -> {
            configPag();
        });

        binding.ivArrowBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setData() {
        if(getIntent().hasExtra("boleto")){
            boleto = (Boleto) getIntent().getSerializableExtra("boleto");

            boleto.setData(System.currentTimeMillis());
            binding.tvPara.setText(boleto.getPara());
            binding.tvValor.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(boleto.getValor())));
            binding.tvData.setText(GetMask.getDate(boleto.getData(), 1));
            binding.tvCodigo.setText(boleto.getCodigo());
        }
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void configPag() {

        if (tipoPagamento.equals("saldo")) {
            pagarComSaldo();
        }

        if(tipoPagamento.equals("cartao")){
            pagarComCartao();
        }

        if(tipoPagamento.equals("fatura") || tipoPagamento.equals("pagarFatura")){
            pagarFatura();
        }
    }

    private void pagarComSaldo() {
        if(usuario != null){
            if(usuario.getSaldo() >= boleto.getValor()){
                usuario.setSaldo(usuario.getSaldo() - boleto.getValor());
                usuario.atualizarSaldo();
                salvarExtrato();
            }else{
                Toast.makeText(this, "Saldo insuficiente.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void salvarExtrato(){

        ExtratoModel extrato =  new ExtratoModel();
        extrato.setOperacao("PAGAMENTO");
        extrato.setValor(boleto.getValor());
        extrato.setTipo("SAIDA");
        extrato.setPessoa(usuario.getId());

        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(usuario.getId())
                .child(extrato.getId());
        extratoRef.setValue(extrato).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DatabaseReference updateExtrato = extratoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

//             enviaNoti(extrato.getId());
             Intent intent = new Intent(ConfirmarPagamento.this, ReciboBoleto.class);
             intent.putExtra("extrato", extrato);
             startActivity(intent);


            }else{
                Toast.makeText(this, "Não foi possivel realizar o pagamento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviaNoti(String idOperacao) {
        Notificacao notificacao = new Notificacao();
        notificacao.setOperação("PAGAMENTO");
        notificacao.setIdDestinario(usuario.getId());
        notificacao.setIdCobrador(usuario.getId());
        notificacao.setIdOperacao(idOperacao);
        notificacao.enviar();
    }

    private void pagarComCartao(){
        if(cartao != null){
            if(cartao.getLimite() >= boleto.getValor()){
                if((cartao.getSaldo() + boleto.getValor()) <= cartao.getLimite()){

                    cartao.setSaldo(cartao.getSaldo() + boleto.getValor());
                    cartao.atualizarSaldo();
                    salvarExtratoCartao();
                }else{
                    Toast.makeText(this, "Limite insuficiente", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(this, "Limite insuficiente", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Não foi possivel recuperar o cartão", Toast.LENGTH_SHORT).show();
        }

    }

    private void salvarExtratoCartao(){

        ExtratoModel extrato =  new ExtratoModel();
        extrato.setOperacao("BOLETO");
        extrato.setValor(boleto.getValor());
        extrato.setTipo(boleto.getPara());
        extrato.setTituloExtrato(boleto.getDesc());
        extrato.setPessoa(usuario.getId());

        DatabaseReference extratoCartaoRef = FirebaseHelper.getDatabaseReference()
                .child("extratosCartao")
                .child(usuario.getId())
                .child(cartao.getTipo())
                .child(extrato.getId());

        extratoCartaoRef.setValue(extrato).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DatabaseReference updateExtrato = extratoCartaoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

//                enviaNoti(extrato.getId());
                Intent intent = new Intent(ConfirmarPagamento.this, ReciboBoleto.class);
                intent.putExtra("extrato", extrato);
                startActivity(intent);


            }else{
                Toast.makeText(this, "Não foi possivel realizar o pagamento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pagarFatura(){
        if(cartao != null){
            if(usuario.getSaldo() >= cartao.getSaldo()){
                double valor = cartao.getSaldo();
                cartao.setSaldo(0);
                usuario.setSaldo(usuario.getSaldo() - valor);
                cartao.atualizarSaldo();
                usuario.atualizarSaldo();

                Intent intent = new Intent(ConfirmarPagamento.this, CartaoFatura.class);
                intent.putExtra("cartao", cartao);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }else{
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(this, "Não foi possivel recuperar o cartão", Toast.LENGTH_SHORT).show();
        }
    }




}