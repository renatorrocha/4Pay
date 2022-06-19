package com.example.appbanco.view.Pagamentos.Recarga;

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
import com.example.appbanco.model.Recarga;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RecargaReciboActivity extends AppCompatActivity {

    private TextView tvCodigoRecarga;
    private TextView tvDataRecarga;
    private TextView tvValorRecarga;
    private TextView tvNumeroRecarga;
    private Button   buttonRecibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_recibo);

        iniciaComponentes();
        getRecarga();

        buttonRecibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecargaReciboActivity.this, Home.class));
            }
        });
    }

    private void getRecarga(){
        String idRecarga = (String) getIntent().getSerializableExtra("idRecarga");

        DatabaseReference recargaRef = FirebaseHelper.getDatabaseReference()
                .child("recargas")
                .child(idRecarga);

        recargaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recarga recarga = snapshot.getValue(Recarga.class);
                configDados(recarga);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados(Recarga recarga){
        tvCodigoRecarga.setText(recarga.getId());
        tvDataRecarga.setText(GetMask.getDate(recarga.getData(), 3));
        tvValorRecarga.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(recarga.getValor())));
        tvNumeroRecarga.setText(recarga.getNumero());
    }

    private void iniciaComponentes(){
        tvCodigoRecarga =findViewById(R.id.tvCodigoRecarga);
        tvDataRecarga = findViewById(R.id.tvDataRecarga);
        tvValorRecarga = findViewById(R.id.tvValorRecarga);
        tvNumeroRecarga = findViewById(R.id.tvNumeroRecarga);
        buttonRecibo = findViewById(R.id.buttonRecibo);
    }
}