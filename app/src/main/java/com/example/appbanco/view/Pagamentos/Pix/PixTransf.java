package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.databinding.ActivityPixTransfBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Transferencia;

import java.util.Date;
import java.util.Locale;

public class PixTransf extends AppCompatActivity {

    ActivityPixTransfBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        binding.tvValorSaldo.setText("R$ " + intent.getStringExtra("userSaldo"));
        binding.etValorPix.setLocale(new Locale("PT", "br"));

        binding.btnPixProximo.setOnClickListener(view -> {
            validaDados(view);
        });
    }

    public void validaDados(View view) {
        double value = (double) binding.etValorPix.getRawValue() / 100;
        if (value > 0) {

            Transferencia transf = new Transferencia();
            transf.setIdUserOrigem(FirebaseHelper.getIdFirebase());
            transf.setValor(value);

            Intent intent = new Intent(this, PixTransfDestino.class);
            intent.putExtra("transferencia", transf);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Digite um valor maior que 0.", Toast.LENGTH_SHORT).show();
        }
    }

}