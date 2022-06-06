package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appbanco.R;
import com.example.appbanco.adapter.Photo;
import com.example.appbanco.adapter.Photoadapter;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.view.Login_Cadastro.Login;

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

                    case 6:
                        FirebaseHelper.getAuth().signOut();
                        startActivity(new Intent(HomeConfigs.this, Login.class));
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

        e = new Photo(R.drawable.ic_sec, "Segurança");
        photos.add(e);

        e = new Photo(R.drawable.config, "Configuração");
        photos.add(e);

        e = new Photo(R.drawable.ic_not, "Notificação");
        photos.add(e);

        e = new Photo(R.drawable.ic_card, "Configurar Cartão");
        photos.add(e);

        e = new Photo(R.drawable.ic_sobre, "Sobre");
        photos.add(e);

        e = new Photo(R.drawable.ic_lock, "Sair");
        photos.add(e);

        return photos;
    }


}