package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagarCobrancaBinding;
import com.example.appbanco.databinding.ActivityPagarCobrancaReciboBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.model.Pagamento;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransfSucesso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PagarCobrancaRecibo extends AppCompatActivity {

    ActivityPagarCobrancaReciboBinding binding;
    private Usuario userDestino;
    private Pagamento pagamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagarCobrancaReciboBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaDados();

        binding.buttonRecibo.setOnClickListener(view1 -> {
            Intent intent = new Intent(PagarCobrancaRecibo.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void recuperaDados(){
        String idPagamento = (String) getIntent().getSerializableExtra("idPagamento");

        DatabaseReference pagRef = FirebaseHelper.getDatabaseReference()
                .child("pagamentos")
                .child(idPagamento);
        pagRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pagamento = snapshot.getValue(Pagamento.class);

                getUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(pagamento.getIdUserDestino());
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

    private void configDados(){
        binding.tvCodigo.setText(pagamento.getId());
        binding.tvPessoa.setText(userDestino.getNome());
        binding.tvData.setText(GetMask.getDate(pagamento.getData(), 3));
        binding.tvValor.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(pagamento.getValor())));
    }
}