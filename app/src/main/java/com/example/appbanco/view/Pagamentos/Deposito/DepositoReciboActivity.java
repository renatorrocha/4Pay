package com.example.appbanco.view.Pagamentos.Deposito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DepositoReciboActivity extends AppCompatActivity {

    private TextView tvCodigoDeposito;
    private TextView tvDataDeposito;
    private TextView tvValorDeposito;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito_recibo);

        iniciaComponentes();

        button.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepositoReciboActivity.this, Home.class);
            }
        });
    }

    private void getDeposito(){
        String idDeposito = (String) getIntent().getSerializableExtra("idDeposito");

        DatabaseReference depositoRef = FirebaseHelper.getDatabaseReference()
                .child("depositos")
                .child(idDeposito);
        depositoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Deposito deposito = snapshot.getValue(Deposito.class);
                configDados(deposito);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados(Deposito deposito){
        tvCodigoDeposito.setText(deposito.getId());
        tvDataDeposito.setText(GetMask.getDate(deposito.getData(), 3));
        tvValorDeposito.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(deposito.getValor())));
    }

    private void iniciaComponentes(){
        tvCodigoDeposito.findViewById(R.id.tvCodigoDeposito);
        tvDataDeposito.findViewById(R.id.tvDataDeposito);
        tvValorDeposito.findViewById(R.id.tvValorDeposito);
    }
}