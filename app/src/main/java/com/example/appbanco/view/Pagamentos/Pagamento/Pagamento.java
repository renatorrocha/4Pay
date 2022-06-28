package com.example.appbanco.view.Pagamentos.Pagamento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityPagamentoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cartao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Pagamento extends AppCompatActivity {

    private ActivityPagamentoBinding binding;
    private List<Cartao> cartaoList = new ArrayList<>();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperarCartoes();

        binding.ivArrowBack.setOnClickListener(v -> {
            finish();
        });

        binding.clBoleto.setOnClickListener(v -> {
            startActivity(new Intent(this, PagarBoleto.class));
        });
        binding.clFatura.setOnClickListener(v -> {
            showDialogTipoCartao();
        });
    }

    private void showDialogTipoCartao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_escolhercartao, null);
        ConstraintLayout clMain = view.findViewById(R.id.clMain);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        Button btnCartaoUm = view.findViewById(R.id.btnCartaoUm);
        Button btnCartaoDois = view.findViewById(R.id.btnCartaoDois);
        Button btnCartaoTres = view.findViewById(R.id.btnCartaoTres);

        ivClose.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        if (cartaoList.size() < 1) {
            btnCartaoUm.setText("Fechar");

            btnCartaoUm.setOnClickListener(v -> {
                dialog.dismiss();
            });

            Toast.makeText(this, "Nenhum cartão encontrado.", Toast.LENGTH_LONG).show();
            clMain.removeView(btnCartaoDois);
            clMain.removeView(btnCartaoTres);


        } else {

                Intent intentNext = new Intent(Pagamento.this, ConfirmarPagamento.class);
                intentNext.putExtra("tipoPagamento", "pagarFatura");

                btnCartaoUm.setText(cartaoList.get(0).getTipo());
                btnCartaoUm.setOnClickListener(v -> {
                    intentNext.putExtra("tipoCartao", cartaoList.get(0));
                    startActivity(intentNext);
                });


                if (cartaoList.size() == 1) {
                    clMain.removeView(btnCartaoDois);
                    clMain.removeView(btnCartaoTres);
                }

                if (cartaoList.size() == 2) {
                    btnCartaoDois.setText(cartaoList.get(1).getTipo());
                    btnCartaoDois.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(1));
                        startActivity(intentNext);
                    });

                    clMain.removeView(btnCartaoTres);
                }

                if (cartaoList.size() == 3) {
                    btnCartaoDois.setText(cartaoList.get(1).getTipo());
                    btnCartaoDois.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(1));
                        startActivity(intentNext);
                    });

                    btnCartaoTres.setText(cartaoList.get(2).getTipo());
                    btnCartaoTres.setOnClickListener(v -> {
                        intentNext.putExtra("tipoCartao", cartaoList.get(2));
                        startActivity(intentNext);
                    });

                    clMain.removeView(btnCartaoTres);
                }


        }

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    private void recuperarCartoes() {
        DatabaseReference cartoesRef = FirebaseHelper.getDatabaseReference()
                .child("cartoes")
                .child(FirebaseHelper.getIdFirebase());
        cartoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartaoList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Cartao cartao = ds.getValue(Cartao.class);
                    cartaoList.add(cartao);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}