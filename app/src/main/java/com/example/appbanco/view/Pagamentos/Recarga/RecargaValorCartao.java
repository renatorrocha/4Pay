package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaValorCartaoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Deposito.DepositoReciboActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.Locale;

public class RecargaValorCartao extends AppCompatActivity {

    private ActivityRecargaValorCartaoBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_valor_cartao);

        binding = ActivityRecargaValorCartaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaValorCartao.this, Home.class);
                startActivity(intent);
                Toast.makeText(RecargaValorCartao.this, "Sua Recarga foi efetuada!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}