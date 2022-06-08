package com.example.appbanco.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.model.ExtratoModel;

import java.util.ArrayList;
import java.util.List;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ViewHolder>{

    private List<ExtratoModel> list = new ArrayList<>();

    public ExtratoAdapter(List<ExtratoModel> list){
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvExtTitulo, tvExtPessoa, tvExtData, tvExtValor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExtTitulo = itemView.findViewById(R.id.tvExtTitulo);
            tvExtPessoa = itemView.findViewById(R.id.tvExtPessoa);
            tvExtData = itemView.findViewById(R.id.tvExtData);
            tvExtValor = itemView.findViewById(R.id.tvExtValor);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View extratoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.extrato_list, parent, false);
        return new ExtratoAdapter.ViewHolder(extratoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtratoModel item = list.get(position);

        holder.tvExtTitulo.setText(item.getTituloExtrato());
        holder.tvExtPessoa.setText(item.getPessoa());
        holder.tvExtData.setText(Long.toString(item.getData()));
        holder.tvExtValor.append(Double.toString(item.getValor()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
