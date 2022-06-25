package com.example.appbanco.view.Login_Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityCadastroProximoBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.MyApi;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroProximo extends AppCompatActivity {

    private ActivityCadastroProximoBinding binding;
    private String cep;
    private Endereco endereco;
    private ProgressBar progressbar;
//    private TextView tvLimiteCartao;
//    private SeekBar sbAjusteLimite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroProximoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
        binding.btnCadastrar.setOnClickListener(view -> {
            validaDados();
        });

        binding.edtcelular.addTextChangedListener(CustomMask.Companion.mask("(##) #####-####", binding.edtcelular, null));
        binding.edtcep.addTextChangedListener(CustomMask.Companion.mask("#####-###", binding.edtcep, null));


        binding.edtcep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 9) {

                    cep = s.toString();
                    buscaEndereco(cep);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//
//        sbAjusteLimite = (SeekBar)findViewById(R.id.sbAjusteLimite);
//        tvLimiteCartao = (TextView)findViewById(R.id.tvLimiteCartao);

//        sbAjusteLimite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                tvLimiteCartao.setText("R$ " + String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }


    private void buscaEndereco(String cep) {

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
                    binding.edtcomplemento.setText(response.body().getComplemento());

                }

            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {

            }
        });
    }

    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.btnCadastrar.setOnClickListener(v -> validaDados());
    }

    private void validaDados() {
        String cel = binding.edtcelular.getText().toString();
        String cep = binding.edtcep.getText().toString();
        String uf = binding.edtuf.getText().toString();
        String numero = binding.edtnumb.getText().toString();
        String logradouro = binding.edtlogradouro.getText().toString();
        String bairro = binding.edtbairro.getText().toString();
        String municipio = binding.edtmunicipio.getText().toString();


        if (!cel.isEmpty()) {
            if (!cep.isEmpty()) {
                if (!uf.isEmpty()) {
                    if (!logradouro.isEmpty()) {
                        if (!bairro.isEmpty()) {
                            if (!municipio.isEmpty()) {

                                ocultaTeclado();

                                //binding.progressbar.setVisibility(View.VISIBLE);


                                if (endereco == null) endereco = new Endereco();
                                endereco.setCep(cep);
                                endereco.setUf(uf);
                                endereco.setNumero(numero);
                                endereco.setLogradouro(logradouro);
                                endereco.setBairro(bairro);
                                endereco.setLocalidade(municipio);
                                endereco.salvar();
                                salvarDadosUsuario(endereco);
                                finish();


                            } else {

                                binding.edtmunicipio.requestFocus();
                                binding.edtmunicipio.setError("Informação obrigatória.");
                            }
                        } else {
                            binding.edtbairro.requestFocus();
                            binding.edtbairro.setError("Informação obrigatória.");
                        }
                    } else {
                        binding.edtlogradouro.requestFocus();
                        binding.edtlogradouro.setError("Informação obrigatória.");
                    }
                } else {
                    binding.edtuf.requestFocus();
                    binding.edtuf.setError("Informação obrigatória.");
                }
            } else {
                binding.edtcep.requestFocus();
                binding.edtcep.setError("Informação obrigatória.");

            }
        } else {
            binding.edtcelular.requestFocus();
            binding.edtcelular.setError("Informação obrigatória.");
        }

    }

    private void salvarDadosUsuario(Endereco endereco) {

        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(Objects.requireNonNull(FirebaseHelper.getAuth().getUid()))
                .child("Endereço");

        usuarioRef.setValue(endereco).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                getUserData();
                finish();
                startActivity(new Intent(this, Home.class));
            } else {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ocultaTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtcep.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                user.setCelular(binding.edtcelular.getText().toString());
                user.setRendimento(Integer.toString(binding.seekBar.getProgress()));
                salvarUser(user);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void salvarUser(Usuario usuario){
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario);

    }

}





