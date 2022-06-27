package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixCobrarFinalBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cobranca;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Transferencia;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransfSucesso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PixCobrarFinal extends AppCompatActivity {

    ActivityPixCobrarFinalBinding binding;
    private Cobranca cobranca;
    private Usuario userDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixCobrarFinalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaUsuarioDestino();

        binding.btnProximo.setOnClickListener(view -> {
            confirmarCobranca(view);
        });

    }

    public void confirmarCobranca(View view) {
        DatabaseReference cobrancaRef = FirebaseHelper.getDatabaseReference()
                .child("cobrancas")
                .child(cobranca.getIdDestinatario())
                .child(cobranca.getId());

        cobrancaRef.setValue(cobranca).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DatabaseReference updateRef = cobrancaRef
                        .child("data");
                updateRef.setValue(ServerValue.TIMESTAMP);

                enviaNoti();

                Intent intent = new Intent(PixCobrarFinal.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Não foi possível confirmar a cobrança.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void enviaNoti() {
        Notificacao notificacao = new Notificacao();
        notificacao.setOperação("COBRANCA");
        notificacao.setIdDestinario(cobranca.getIdDestinatario());
        notificacao.setIdCobrador(FirebaseHelper.getIdFirebase());
        notificacao.setIdOperacao(cobranca.getId());
        notificacao.enviar();
    }

    private void recuperaUsuarioDestino() {
        cobranca = (Cobranca) getIntent().getSerializableExtra("cobranca");

        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(cobranca.getIdDestinatario());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
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


    private void configDados() {

        binding.tvUserDestino.setText("Para " + userDestino.getNome());
        binding.tvValorCobranca.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cobranca.getValor())));

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formataData.format(System.currentTimeMillis());

        binding.tvData.setText(dataFormatada);

    }

}