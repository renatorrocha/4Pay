package com.example.appbanco.view.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.databinding.ActivityHomeBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Pix.PixTransf;
import com.example.appbanco.view.Pagamentos.Recarga.Recarga;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivPerson.setOnClickListener(view1 -> {
            Intent intent = new Intent( this, HomeConfigs.class);
            intent.putExtra("nomeUser", binding.tvBemVindo.getText());
            startActivity(intent);
        });

        binding.clSaldoExt.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Extrato.class));
        });


//        binding.tvPagamentos.setOnClickListener(view1 -> {
//            startActivity(new Intent(this, ));
//        });

        binding.clCartao.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Cartoes.class));
        });

        binding.clTransf.setOnClickListener(view1 -> {
            startActivity(new Intent(this, PixTransf.class));
        });

        binding.clRecarga.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Recarga.class));
        });

    }
    @Override
    protected void onStart(){
        super.onStart();

        getUserData();

        binding.clPix.setOnClickListener(view1 -> {
            Intent intent = new Intent( this, PixTransf.class);
            intent.putExtra("userSaldo", binding.tvSaldoValor.getText().toString());
            startActivity(intent);
        });
    }

    private void getUserData(){
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                binding.tvSaldoValor.setText(Double.toString(user.getSaldo()));

                String[] splitName = user.getNome().trim().split("\\s+");
                binding.tvBemVindo.setText("Bom dia, " + splitName[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}