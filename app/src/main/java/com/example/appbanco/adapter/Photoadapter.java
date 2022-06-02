package com.example.appbanco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanco.R;

import java.util.ArrayList;

public class Photoadapter extends ArrayAdapter<Photo> {

    private final Context context;
    private final ArrayList<Photo> elementos;

    public Photoadapter(Context context, ArrayList<Photo> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);
        TextView tvtitle=(TextView) rowView.findViewById(R.id.tvtitle);


        imagem.setImageResource(elementos.get(position).getFoto());
        tvtitle.setText(elementos.get(position).getTitulo());


        return rowView;
    }
}

