package com.example.appbanco.view.Home.HomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ExtratoAdapter;
import com.example.appbanco.databinding.FragmentExtratoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ExtratoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExtratoFragment extends Fragment {

    FragmentExtratoBinding binding;
    private List<ExtratoModel> list = new ArrayList<>();
    private ExtratoAdapter extratoAdapter;
    private RecyclerView rvExtrato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding = FragmentExtratoBinding.inflate(getLayoutInflater());
       View view = binding.getRoot();

        recuperarExtrato();

        rvExtrato = view.findViewById(R.id.rv_extrato);
        rvExtrato.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvExtrato.setHasFixedSize(true);
        extratoAdapter = new ExtratoAdapter(list, view.getContext());
        rvExtrato.setAdapter(extratoAdapter);

       return view;
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
                    Toast.makeText(getView().getContext(), "Nenhum extrato encontrado.", Toast.LENGTH_SHORT).show();
                }

                extratoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}