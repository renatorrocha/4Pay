package com.example.appbanco.view.Pagamentos.Cartoes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbanco.databinding.ActivityCartaoCriarSenhaBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.google.firebase.database.DatabaseReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CartaoCriarSenha extends AppCompatActivity {

    private Usuario usuario;
    private ActivityCartaoCriarSenhaBinding binding;
    private GetUserViewModel userViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartaoCriarSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(GetUserViewModel.class);
        userViewModel.verifyUserData();

        userViewModel.getUser.observe(this, sucess -> {
            if (sucess) {
                usuario = userViewModel.getUser();
            }
        });

        binding.tvTipoCartao.setText((String) getIntent().getSerializableExtra("tipoCartao"));

        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnCriar.setOnClickListener(view -> {
            validarDados();
        });

    }


    private void criarCartao(Cartao cartao) {

        DatabaseReference cartaoRef = FirebaseHelper.getDatabaseReference()
                .child("cartoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(cartao.getTipo());

        cartaoRef.setValue(cartao).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Cartão criado.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, CartaoCriado.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Não foi possivel criar o cartão.", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validarDados() {
        String senha = binding.senha.getText().toString();
        String repitaSenha = binding.repitaSenha.getText().toString();

        if (senha != null) {
            if (repitaSenha != null) {
                if (senha.length() == 4) {
                    if (senha.equals(repitaSenha)) {
                        String tipoCartao = (String) getIntent().getSerializableExtra("tipoCartao");
                        Cartao cartao = new Cartao();

                        cartao.setTipo(tipoCartao);
                        cartao.setIdDono(usuario.getId());
                        cartao.setNome(usuario.getNome());
                        cartao.setSenha(senha);
                        cartao.gerarPin();
                        cartao.gerarCodigoCartao();

                        int rendimento = Integer.parseInt(usuario.getRendimento());
                        if (rendimento > 0 && rendimento < 2500) {
                            cartao.setLimite(1600);
                        } else if (rendimento >= 2500 && rendimento < 5000) {
                            cartao.setLimite(2400);
                        } else if (rendimento >= 5000 && rendimento < 7500) {
                            cartao.setLimite(3600);
                        } else if (rendimento >= 7500) {
                            cartao.setLimite(6500);
                        } else if (rendimento == 0) {
                            cartao.setLimite(300);
                        }

                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM/YY");
                        LocalDate date = LocalDate.now();
                        LocalDate dateVencimento = date.plus(8, ChronoUnit.YEARS);

                        cartao.setDataCriacao(timeFormatter.format(date));
                        cartao.setDataVencimento(timeFormatter.format(dateVencimento));

                        criarCartao(cartao);


                    } else {
                        binding.edtRepitaSenha.requestFocus();
                        binding.edtsenha.setError("As senhas precisam ser iguais");
                    }


                } else {
                    binding.edtsenha.requestFocus();
                    binding.edtsenha.setError("A senha precisa ter 4 digitos");
                }

            } else {
                binding.edtRepitaSenha.requestFocus();
                binding.edtRepitaSenha.setError("Confirme sua senha.");
            }

        } else {
            binding.edtsenha.requestFocus();
            binding.edtsenha.setError("Informe sua senha.");
        }
    }


}