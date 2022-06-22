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
import java.util.Locale;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.ViewHolder>{

    private List<ExtratoModel> list = new ArrayList<>();
    private Context context;

    public ExtratoAdapter(List<ExtratoModel> list, Context baseContext){
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View extratoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.extrato_list, parent, false);
        return new ExtratoAdapter.ViewHolder(extratoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtratoModel item = list.get(position);

        String tipo = item.getTipo().toLowerCase();
        String operacao = item.getOperacao().toLowerCase();
        holder.tvExtTitulo.setText(operacao.substring(0,1).toUpperCase() + operacao.substring(1));
        holder.tvExtTipo.setText(tipo.substring(0,1).toUpperCase() + tipo.substring(1));
        holder.tvExtValor.setText(context.getString(R.string.txt_valor_deposito, GetMask.getValor(item.getValor())));
        holder.tvExtData.setText(GetMask.getDate(item.getData(), 1));


        if(item.getOperacao().equals("TRANSFERENCIA")){
            if(item.getTipo().equals("SAIDA")){
                holder.ivExt.setImageResource(R.drawable.ic_transf_saida);
                holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.vermelho));

            }
        }
        if(item.getOperacao().equals("TRANSFERENCIA")){
            if(item.getTipo().equals("ENTRADA")){
                holder.ivExt.setImageResource(R.drawable.ic_transf_entrada);
                holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.teal_700));

            }
        }

        if(item.getOperacao().equals("RECARGA")){
            holder.ivExt.setImageResource(R.drawable.ic_recarga);
            holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
        }

        if(item.getOperacao().equals("PAGAMENTO")){
            if(item.getTipo().equals("SAIDA")){
                holder.ivExt.setImageResource(R.drawable.ic_pag_saida);
                holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.vermelho));

            }
        }
        if(item.getOperacao().equals("PAGAMENTO")){
            if(item.getTipo().equals("ENTRADA")){
                holder.ivExt.setImageResource(R.drawable.ic_pag_entrada);
                holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.teal_700));

            }
        }

        if(item.getOperacao().equals("DEPOSITO")){
            holder.ivExt.setImageResource(R.drawable.ic_deposit_entrada);
            holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.teal_700));
        }


//        if(item.getTipo().equals("SAIDA")){
//            holder.tvExtValor.setTextColor(ContextCompat.getColor(context, R.color.vermelho));
//        }
//
//        if(item.getTipo().equals("ENTRADA")){
//        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
