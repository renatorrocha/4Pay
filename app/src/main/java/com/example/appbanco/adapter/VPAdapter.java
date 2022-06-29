package com.example.appbanco.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.SeguroModel;
import com.example.appbanco.model.ViewPagerItem;
import com.example.appbanco.view.Home.Seguros;
import com.example.appbanco.view.Pagamentos.Pix.Chave_Pix.ChaveCriada;
import com.google.firebase.database.DatabaseReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {


    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    Context context;

    public VPAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList, Context context) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);

        holder.imageView.setImageResource(viewPagerItem.getImgID());
        holder.tvTitulo.setText(viewPagerItem.getTitulo());
        holder.tvDescricao.setText(viewPagerItem.getDescricao());

        holder.btnContratar.setOnClickListener(v -> {
            enviarSeguro(viewPagerItem);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void enviarSeguro(ViewPagerItem item) {
        if (item.getTitulo() != null) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/YY");
            LocalDate date = LocalDate.now();
            LocalDate dateVencimento = date.plus(8, ChronoUnit.YEARS);

            if (item.getTitulo().equals("Seguro De Cartão")) {
                SeguroModel seguro = new SeguroModel(item.getTitulo(), 130, item.getDescricao(), true, timeFormatter.format(dateVencimento));
                setSeguro(seguro);

            } else if (item.getTitulo().equals("Seguro De Vida")) {
                SeguroModel seguro = new SeguroModel(item.getTitulo(), 260, item.getDescricao(), true, timeFormatter.format(dateVencimento));
                setSeguro(seguro);


            }

        }
    }

    private void setSeguro(SeguroModel seguro){

            DatabaseReference seguroRef = FirebaseHelper.getDatabaseReference()
                    .child("seguros")
                    .child(FirebaseHelper.getIdFirebase())
                    .child(seguro.getTipo());

        seguroRef.setValue(seguro).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Seguro contratado com sucesso.", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(context, "Não foi possivel contratado o seguro.", Toast.LENGTH_SHORT).show();

                }
            });

    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitulo, tvDescricao;
        Button btnContratar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            btnContratar = itemView.findViewById(R.id.btnContratar);

        }
    }
}
