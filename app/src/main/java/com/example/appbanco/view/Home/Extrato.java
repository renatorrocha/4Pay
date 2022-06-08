package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ExtratoAdapter;
import com.example.appbanco.databinding.ActivityExtratoBinding;
import com.example.appbanco.model.ExtratoModel;

import java.util.ArrayList;
import java.util.List;

public class Extrato extends AppCompatActivity {

    ActivityExtratoBinding binding;
    private List<ExtratoModel> list;
    private ExtratoModel extModel;
    /*Extrato.ViewHolder extViewHolder = new Extrato.ViewHolder();
    RecyclerView.Adapter extAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExtratoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        extViewHolder.rv_extrato = findViewById(R.id.rv_extrato);
        extViewHolder.rv_extrato.setHasFixedSize(true);
        extViewHolder.rv_extrato.setLayoutManager(new LinearLayoutManager(this));

        /*list = getExtratoList();
        extAdapter = new ExtratoAdapter(list);
        extViewHolder.rv_extrato.setAdapter(extAdapter);



    }

    public List<ExtratoModel> getExtratoList(){
        List<ExtratoModel> list = new ArrayList<>(6);
        list.add(0,new ExtratoModel("Transferencia Enviada", "Marcos", "600,00", "06 JUN"));
        list.add(1,new ExtratoModel("Transferencia Recebida", "Maria", "1587,50", "02 JUN"));
        list.add(2,new ExtratoModel("Transferencia Recebida", "Joana", "325,00", "28 MAI"));
        list.add(3,new ExtratoModel("Transferencia Enviada", "Francisco", "936,00", "25 MAI"));
        list.add(4,new ExtratoModel("Transferencia Enviada", "Gabriel", "400,00", "15 MAI"));
        list.add(5,new ExtratoModel("Transferencia Recebida", "Pedro", "1300,00", "14 MAI"));
        return list;
    }; */

    public static class ViewHolder{
        RecyclerView rv_extrato;
    }
}