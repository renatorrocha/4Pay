package com.example.appbanco.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ExtratoModel;

import java.util.ArrayList;
import java.util.List;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ViewHolder>{

    private List<ExtratoModel> list = new ArrayList<>();
    private Context context;

    public ExtratoAdapter(List<ExtratoModel> list, Context baseContext){
        this.list = list;
        this.context = baseContext;
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

        holder.tvExtTitulo.setText(item.getOperacao());
        holder.tvExtPessoa.setText(item.getTipo());
        holder.tvExtValor.append(context.getString(R.string.txt_valor_saldo, GetMask.getValor(item.getValor())));
        holder.tvExtData.setText(GetMask.getDate(item.getData(), 3));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
