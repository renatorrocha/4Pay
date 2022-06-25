package com.example.appbanco.view.Pagamentos.Cartoes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityCartaoFaturaBinding;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cartao;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CartaoFatura extends AppCompatActivity {

    BottomSheetBehavior bottomSheetBehavior;
    ActivityCartaoFaturaBinding binding;
    private Cartao cartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoFaturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(view -> { finish();});

        if(getIntent().hasExtra("cartao")){
            cartao = (Cartao) getIntent().getSerializableExtra("cartao");
            binding.tvTipoCartao.setText(cartao.getTipo());
            binding.tvPin.setText(cartao.getPin());
            binding.tvNumCartao.setText(cartao.getNumeros());
            binding.tvDataCriacao.setText(cartao.getDataCriacao());
            binding.tvDataValidade.setText(cartao.getDataVencimento());
            binding.tvValidadeCartao.setText(cartao.getDataVencimento());
            binding.tvValorFatura.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getSaldo())));
            binding.tvValorDisponivel.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getSaldo())));
            binding.tvValorLimite.setText(getString(R.string.txt_valor_deposito, GetMask.getValor(cartao.getLimite() - cartao.getSaldo())));
        }


        ConstraintLayout clBehavior = findViewById(R.id.clBehavior);
        bottomSheetBehavior = BottomSheetBehavior.from(clBehavior);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


    }

}