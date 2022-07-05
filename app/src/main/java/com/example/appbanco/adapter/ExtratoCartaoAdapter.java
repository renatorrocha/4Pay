package com.example.appbanco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ExtratoModel;

import java.util.ArrayList;
import java.util.List;

public class ExtratoCartaoAdapter extends RecyclerView.Adapter<ExtratoCartaoAdapter.ViewHolder> {

    private List<ExtratoModel> list = new ArrayList<>();
    private Context context;

    public ExtratoCartaoAdapter(List<ExtratoModel> list, Context baseContext) {
        this.list = list;
        this.context = baseContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvExtTitulo, tvExtTipo, tvExtData, tvExtValor;
        private ImageView ivExt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExtTitulo = itemView.findViewById(R.id.tvExtTitulo);
            tvExtTipo = itemView.findViewById(R.id.tvExtTipo);
            tvExtData = itemView.findViewById(R.id.tvExtData);
            tvExtValor = itemView.findViewById(R.id.tvExtValor);
            ivExt = itemView.findViewById(R.id.ivExt);
        }
    }

    @NonNull
    @Override
    public ExtratoCartaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View extratoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.extrato_cartao_list, parent, false);
        return new ExtratoCartaoAdapter.ViewHolder(extratoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtratoCartaoAdapter.ViewHolder holder, int position) {
        ExtratoModel item = list.get(position);

        String tipo = item.getTipo().toLowerCase();
        String operacao = item.getOperacao().toLowerCase();
        holder.tvExtTitulo.setText(tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
        holder.tvExtValor.setText(context.getString(R.string.txt_valor_deposito, GetMask.getValor(item.getValor())));
        holder.tvExtData.setText(GetMask.getDate(item.getData(), 1));
        holder.tvExtTipo.setText(item.getTituloExtrato());

        if (item.getOperacao().equals("BOLETO")) {
            holder.ivExt.setImageResource(R.drawable.ic_barcode);
            holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
