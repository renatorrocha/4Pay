package com.example.appbanco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.Notificacao;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ChavesPixAdapter extends RecyclerView.Adapter<ChavesPixAdapter.ViewHolder> {

    private List<ChavePix> list = new ArrayList<>();


    public ChavesPixAdapter(List<ChavePix> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvChave, tvChaveUsuario;
        private ImageView ivTipoChave, ivClose;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChave = itemView.findViewById(R.id.tvChave);
            tvChaveUsuario = itemView.findViewById(R.id.tvChaveUsuario);
            ivTipoChave = itemView.findViewById(R.id.ivTipoChave);
            ivClose = itemView.findViewById(R.id.ivClose);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chavesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chavespix, parent, false);
        return new ChavesPixAdapter.ViewHolder(chavesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChavePix item = list.get(position);

        String setTipoChave = item.getTipoChave().substring(0, 1).toUpperCase() + item.getTipoChave().substring(1);

        holder.tvChave.setText(setTipoChave);
        holder.tvChaveUsuario.setText(item.getChave());

        if(item.getTipoChave().equals("celular")){
            holder.ivTipoChave.setImageResource(R.drawable.ic_cel);

        } else if(item.getTipoChave().equals("cpf")){
            holder.ivTipoChave.setImageResource(R.drawable.ic_id);

        }else if(item.getTipoChave().equals("email")){
            holder.ivTipoChave.setImageResource(R.drawable.ic_email);

        }else if(item.getTipoChave().equals("chaveAleatoria")){
            holder.ivTipoChave.setImageResource(R.drawable.ic_sec);
        }

        holder.ivClose.setOnClickListener(view1 -> {
            removeChave(item);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void removeChave(ChavePix chavePix) {
        DatabaseReference chaveRef = FirebaseHelper.getDatabaseReference()
                .child("chavesPix")
                .child(FirebaseHelper.getIdFirebase())
                .child(chavePix.getTipoChave());
        chaveRef.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

            }else {
            }


        });
    }


}
