package com.example.appbanco.view.Pagamentos.Pix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPixTransfBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Transferencia;

public class PixTransf extends AppCompatActivity {

    ActivityPixTransfBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPixTransfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        binding.tvValorSaldo.setText("R$ " + intent.getStringExtra("userSaldo"));

        binding.btnPixProximo.setOnClickListener(view -> {
            validaDados(view);
        });
    }

    public void validaDados(View view){
        double value = Double.parseDouble(binding.etValorPix.getText().toString());
        if(value > 0){

            Transferencia transf = new Transferencia();
            transf.setIdUserOrigem(FirebaseHelper.getIdFirebase());
            transf.setData(System.currentTimeMillis());
            transf.setValor(value);

            Intent intent = new Intent(this, PixTransfDestino.class);
            intent.putExtra("transferencia", transf);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Digite um valor maior que 0.", Toast.LENGTH_SHORT).show();
        }
    }
}