package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appbanco.adapter.Photo;
import com.example.appbanco.adapter.Photoadapter;

import java.util.ArrayList;

public class HomeConfigs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_configs);


        ListView lista = (ListView) findViewById(R.id._dynamic);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0: Intent intent = new Intent(view.getContext(), DadosUsuario.class);
                        startActivity(intent);
                        break;

                    case 1: Intent intent1 = new Intent(view.getContext(),DadosUsuario.class);
                        break;


                }

            }
        });

        ArrayAdapter adapter = new Photoadapter(this, adicionarPhoto());
        lista.setAdapter(adapter);
    }



    private ArrayList<Photo> adicionarPhoto() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        Photo e = new Photo(R.drawable.ic_perfil, "Meus dados");
        photos.add(e);

        e = new Photo(R.drawable.sec, "Segurança");
        photos.add(e);

        e = new Photo(R.drawable.config, "Configuração");
        photos.add(e);

        e = new Photo(R.drawable.not, "Notificação");
        photos.add(e);
        e = new Photo(R.drawable.card, "Configurar Cartão");
        photos.add(e);

        e = new Photo(R.drawable.sobre, "Sobre");
        photos.add(e);

        return photos;
    }


}