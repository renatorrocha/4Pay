package com.example.appbanco.view.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityDadosUsuarioBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DadosUsuario extends AppCompatActivity {

    ActivityDadosUsuarioBinding binding;


    private ListView listadados;
    private String[] itens = {"Nome de Preferência:", "Email:", "Telefone:",
            "Endereço:", "Renda mensal:", "Consultar senha de 4 digítos:",
            "Extrato anual de juros tarifas e impostos:"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDadosUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivArrowBack.setOnClickListener(view -> {
            startActivity(new Intent(DadosUsuario.this, HomeConfigs.class));
        });

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

    @Override
    protected void onStart(){
        super.onStart();

        getUserData();
    }

    private void getUserData(){
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                String[] splitName = user.getNome().trim().split("\\s+");

                String [] itens = {"Nome de Preferência: "+ splitName[0],
                        "Email: " + user.getEmail(),
                        "Telefone:",
                        "Endereço:",
                        "Renda mensal: "+ user.getSaldo(),
                        "Consultar senha de 4 digítos:",
                        "Extrato anual de juros tarifas e impostos:"};

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        itens
                );
                listadados.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}