package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DadosUsuario extends AppCompatActivity {


    private ListView listadados;
    private String[] itens = {"Nome de Preferência:", "Email:", "Telefone:",
            "Endereço:", "Renda mensal:", "Consultar senha de 4 digítos:",
            "Extrato anual de juros tarifas e impostos:"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_usuario);

        listadados=findViewById(R.id.listdados);

        //Adaptador para a lista

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens


        );

        //adiciona adaptador para a lista

        listadados.setAdapter(adaptador);

        //clique na lista
        listadados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String valorSelecionado = listadados.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),valorSelecionado,Toast.LENGTH_LONG).show();

            }
        });

    }
}