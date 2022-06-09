package com.example.appbanco.view.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ExtratoAdapter;
import com.example.appbanco.databinding.ActivityExtratoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ExtratoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Extrato extends AppCompatActivity {

    ActivityExtratoBinding binding;
    private List<ExtratoModel> list = new ArrayList<>();
    private ExtratoAdapter extratoAdapter;
    private RecyclerView rvExtrato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExtratoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperarExtrato();

        rvExtrato = findViewById(R.id.rv_extrato);
        rvExtrato.setLayoutManager(new LinearLayoutManager(this));
        rvExtrato.setHasFixedSize(true);
        extratoAdapter = new ExtratoAdapter(list, getBaseContext());
        rvExtrato.setAdapter(extratoAdapter);
    }

    private void recuperarExtrato(){
        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(FirebaseHelper.getIdFirebase());
        extratoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for( DataSnapshot ds : snapshot.getChildren()){
                        ExtratoModel extrato = ds.getValue(ExtratoModel.class);
                        list.add(extrato);
                    }
                
                }else {
                    Toast.makeText(Extrato.this, "Nenhum extrato encontrado.", Toast.LENGTH_SHORT).show();
                }

                extratoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}