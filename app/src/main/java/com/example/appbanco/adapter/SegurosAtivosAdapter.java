package com.example.appbanco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.model.ListaSeguro;

import java.util.ArrayList;
import java.util.List;

public class SegurosAtivosAdapter extends RecyclerView.Adapter<SegurosAtivosAdapter.ViewHolder>{

    List<ListaSeguro>listaSeguros = new ArrayList<>();

    public SegurosAtivosAdapter(List<ListaSeguro> listaSeguros) {
        this.listaSeguros = listaSeguros;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSeguroTitulo, tvSeguroDesc, tvSegExpira;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSeguroTitulo = itemView.findViewById(R.id.tvSeguroTitulo);
            tvSeguroDesc = itemView.findViewById(R.id.tvSeguroDesc);
            tvSegExpira = itemView.findViewById(R.id.tvSegExpira);
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

        ListaSeguro item = listaSeguros.get(position);

        holder.tvSeguroTitulo.setText(item.getTituloSeguro());
        holder.tvSeguroDesc.setText(item.getDescSeguro());

    }

    @Override
    public int getItemCount() {
        return listaSeguros.size();
    }
}

