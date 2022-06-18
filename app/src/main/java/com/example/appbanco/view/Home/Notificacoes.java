package com.example.appbanco.view.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.adapter.NotiAdapter;
import com.example.appbanco.databinding.ActivityNotificacoesBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Notificacao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Notificacoes extends AppCompatActivity implements NotiAdapter.OnClick {

    private List<Notificacao> list = new ArrayList<>();
    private NotiAdapter notiAdapter;
    ActivityNotificacoesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNoti.setHasFixedSize(true);
        notiAdapter = new NotiAdapter(list, getBaseContext(), this);
        binding.rvNoti.setAdapter(notiAdapter);

        getAllNoti();

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });


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

    @Override
    public void onClickListener(Notificacao notificacao) {

    }
}