package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaOperadoraBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Pagamentos.Deposito.DepositoReciboActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.Locale;

public class RecargaOperadora extends AppCompatActivity {

    private ActivityRecargaOperadoraBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_operadora);

        binding = ActivityRecargaOperadoraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecargaOperadora.this,RecargaPagamento.class);
                startActivity(intent);
            }
        });
    }

}