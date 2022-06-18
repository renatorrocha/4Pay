package com.example.appbanco.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Notificacoes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {

    private List<Notificacao> list;
    private Context context;
    private OnClick onClick;

    public NotiAdapter(List<Notificacao> list, Context context, OnClick onClick) {
        this.list = list;
        this.context = context;
        this.onClick = onClick;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvCobrador, tvData, tvMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvCobrador = itemView.findViewById(R.id.tvCobrador);
            tvData = itemView.findViewById(R.id.tvData);
            tvMsg = itemView.findViewById(R.id.tvMsg);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View notiView = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item, parent, false);
        return new ViewHolder(notiView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notificacao item = list.get(position);

        String titulo = "";
        switch (item.getOperação()){
            case "COBRANCA":
                titulo = "Você recebeu uma cobrança.";
                break;

            case "TRANSFERENCIA":
                titulo = "Você recebeu uma transferência.";
                break;

            case "PAGAMENTO":
                titulo = "Você recebeu um pagamento.";
                break;
        }

        holder.tvTitulo.setText(titulo);
        holder.tvData.setText(GetMask.getDate(item.getData(), 3));

        if(item.isLida()){
            holder.tvTitulo.setTypeface(null, Typeface.NORMAL);
            holder.tvData.setTypeface(null, Typeface.NORMAL);
            holder.tvMsg.setTypeface(null, Typeface.NORMAL);
        }else{
            holder.tvTitulo.setTypeface(null, Typeface.BOLD);
            holder.tvData.setTypeface(null, Typeface.BOLD);
            holder.tvMsg.setTypeface(null, Typeface.BOLD);
        }

        getUserData(item, holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void getUserData(Notificacao notificacao, ViewHolder holder) {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(notificacao.getIdCobrador());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                if(user != null){
                    holder.tvCobrador.setText(context.getString(R.string.txt_enviada_por, user.getNome()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface OnClick{
        void onClickListener(Notificacao notificacao);
    }

}
