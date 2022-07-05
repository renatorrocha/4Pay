package com.example.appbanco.view.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanco.R;
import com.example.appbanco.adapter.NotiAdapter;
import com.example.appbanco.databinding.ActivityNotificacoesBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.view.Pagamentos.Pix.PixCobrar.PagarCobranca;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notificacoes extends AppCompatActivity implements NotiAdapter.OnClick {

    private List<Notificacao> list = new ArrayList<>();
    private NotiAdapter notiAdapter;
    ActivityNotificacoesBinding binding;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNoti.setHasFixedSize(true);
        notiAdapter = new NotiAdapter(list, getBaseContext(), this);
        binding.rvNoti.setAdapter(notiAdapter);

        binding.rvNoti.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                showDialogRemover(list.get(position));
            }

            @Override
            public void onSwipedRight(int position) {
                showDialogStatus(list.get(position));
            }
        });


        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });

        getAllNoti();


    }

    private void getAllNoti() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(FirebaseHelper.getIdFirebase());
        notiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Notificacao noti = ds.getValue(Notificacao.class);
                        list.add(noti);
                    }

                } else {

                }
                Collections.reverse(list);
                notiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeNoti(Notificacao notificacao) {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(notificacao.getId());
        notiRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.remove(notificacao);

                if (list.isEmpty()) {
                    Toast.makeText(this, "Nenhuma notificação.", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(this, "Notificação removida.", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Não foi possível remover a notificacação.", Toast.LENGTH_SHORT).show();

            }
            notiAdapter.notifyDataSetChanged();

        });
    }

    private void showDialogStatus(Notificacao notificacao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnClose = view.findViewById(R.id.btnClose);
        TextView textTitulo = view.findViewById(R.id.textTitulo);
        TextView textMensagem = view.findViewById(R.id.textMensagem);

        if (notificacao.isLida()) {
            textTitulo.setText("Deseja marcar esta notificação como Não lida?");
            textMensagem.setText("Aperte em sim para marcar como Não lida ou em fechar para cancelar.");
        } else {
            textTitulo.setText("Deseja marcar esta notificação como Lida?");
            textMensagem.setText("Aperte em sim para marcar como Lida ou em fechar para cancelar.");
        }

        btnOk.setOnClickListener(v -> {
            notificacao.salvar();
            dialog.dismiss();
        });

        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
            notiAdapter.notifyDataSetChanged();
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }


    private void showDialogRemover(Notificacao notificacao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnClose = view.findViewById(R.id.btnClose);
        TextView textTitulo = view.findViewById(R.id.textTitulo);
        TextView textMensagem = view.findViewById(R.id.textMensagem);

        textTitulo.setText("Deseja deletar esta notificação?");
        textMensagem.setText("Aperte em sim para deletar ou em fechar para cancelar.");

        btnOk.setOnClickListener(v -> {
            removeNoti(notificacao);
            dialog.dismiss();

        });

        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
            notiAdapter.notifyDataSetChanged();
        });

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void OnClickListener(Notificacao notificacao) {
        if (notificacao.getOperação().equals("COBRANCA")) {
            Intent it = new Intent(this, PagarCobranca.class);
            it.putExtra("notificacao", notificacao);
            startActivity(it);
        }

    }
}