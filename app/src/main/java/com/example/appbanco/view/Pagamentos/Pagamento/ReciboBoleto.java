package com.example.appbanco.view.Pagamentos.Pagamento;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityReciboBoletoBinding;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.view.Home.Home;

public class ReciboBoleto extends AppCompatActivity {

    ActivityReciboBoletoBinding binding;
    ExtratoModel extrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReciboBoletoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaDados();

        binding.buttonRecibo.setOnClickListener(v -> {
            Intent intent = new Intent(ReciboBoleto.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void recuperaDados() {
        extrato = (ExtratoModel) getIntent().getSerializableExtra("extrato");

        binding.tvCodigo.setText(extrato.getId());
        binding.tvData.setText(GetMask.getDate(extrato.getData(), 3));
        binding.tvValor.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(extrato.getValor())));

    }
}