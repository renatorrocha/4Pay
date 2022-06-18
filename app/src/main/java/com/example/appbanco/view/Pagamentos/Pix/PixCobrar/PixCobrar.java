package com.example.appbanco.view.Pagamentos.Pix.PixCobrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixCobrarBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cobranca;
import com.example.appbanco.view.Pagamentos.Pix.PixTransferir.PixTransfDestino;

import java.util.Locale;

public class PixCobrar extends AppCompatActivity {

    private CurrencyEditText edtValor;
    ActivityPixCobrarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixCobrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edtValor = findViewById(R.id.edtValCobrar);
        edtValor.setLocale(new Locale("PT", "br"));

        binding.btnProximo.setOnClickListener(view1 -> {
            double valorCobrar = (double) edtValor.getRawValue() / 100;

            if(valorCobrar >=  10){
                Cobranca cobranca = new Cobranca();
                cobranca.setValor(valorCobrar);
                cobranca.setIdCobrador(FirebaseHelper.getIdFirebase());

                Intent intent = new Intent(this, PixCobrarDestino.class);
                intent.putExtra("cobranca", cobranca);
                startActivity(intent);

            }else{
                Toast.makeText(this, "O valor minimo para cobrança é R$10,00", Toast.LENGTH_SHORT).show();
            }
        });


    }
}