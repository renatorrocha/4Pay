package com.example.appbanco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ListaSeguro;
import com.example.appbanco.model.SeguroModel;

import java.util.ArrayList;
import java.util.List;

public class SegurosAtivosAdapter extends RecyclerView.Adapter<SegurosAtivosAdapter.ViewHolder>{

    List<SeguroModel>listaSeguros = new ArrayList<>();
    private Context context;

    public SegurosAtivosAdapter(List<SeguroModel> listaSeguros, Context context) {
        this.listaSeguros = listaSeguros;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitulo, tvDesc, tvValor, tvVencimento;
        private ImageView ivSeguro;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvVencimento = itemView.findViewById(R.id.tvVencimento);
            ivSeguro = itemView.findViewById(R.id.ivSeguro);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View seguroView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_seguros, parent, false);
        return new SegurosAtivosAdapter.ViewHolder(seguroView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SeguroModel item = listaSeguros.get(position);

       holder.tvTitulo.setText(item.getTipo());
       holder.tvDesc.setText(item.getDesc());
       holder.tvVencimento.setText(item.getDataVencimento());
       holder.tvValor.setText(context.getString(R.string.txt_valor_deposito, GetMask.getValor(item.getValor())));

       if(item.getTipo().equals("Seguro De Cartão")){
           holder.ivSeguro.setImageResource(R.drawable.cartao_credito);

       } else if(item.getTipo().equals("Seguro De Vida")){
           holder.ivSeguro.setImageResource(R.drawable.seguro_de_vida);
       }


    }

    @Override
    public int getItemCount() {
        return listaSeguros.size();
    }
}

