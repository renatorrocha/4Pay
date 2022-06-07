package com.example.appbanco.view.Login_Cadastro;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.appbanco.databinding.ActivityCadastroProximoBinding;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.MyApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroProximo extends AppCompatActivity {

    private ActivityCadastroProximoBinding binding;
    private String cep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroProximoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroProximo.this, TipoConta.class);
                startActivity(intent);
            }
        });

        binding.edtcep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 9){

                    cep = s.toString();
                    buscaEndereco(cep);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void buscaEndereco(String cep){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//instância para interface
        MyApi myApi = retrofit.create(MyApi.class);
        Call<Endereco> call = myApi.getData(cep);
        // retorno = > sucesso e falha
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.code() != 200) {
                    Toast.makeText(CadastroProximo.this, "Verificar conexão", Toast.LENGTH_SHORT).show();
                } else {


                    assert response.body() != null;
                    binding.edtlogradouro.setText(response.body().getLogradouro());
                    binding.edtbairro.setText(response.body().getBairro());
                    binding.edtmunicipio.setText(response.body().getLocalidade());
                    binding.edtuf.setText(response.body().getUf());


                }

            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {

            }
        });
    }

    }


