package com.example.appbanco.view.Pagamentos.Recarga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.example.appbanco.view.Pagamentos.Deposito.DepositoReciboActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

import java.util.Locale;

public class Recarga extends AppCompatActivity {

    private ActivityRecargaBinding binding;

    private CurrencyEditText edtVal;
    private AlertDialog dialog;
    private EditText edtTelefone;
    //private Button btnConfirmar;
    private ImageView ivArrowBack;
    private ProgressBar progressBar;
    //private

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        binding = ActivityRecargaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtTelefone.addTextChangedListener(CustomMask.Companion.mask("(##) #####-####", binding.edtTelefone, null));

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
            }
        });

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recarga.this, RecargaOperadora.class);
                startActivity(intent);

            }
        });

        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));

        });

        iniciaComponentes();

    }

    public void validaDados(View view){

        double valor = (double) edtVal.getRawValue() / 100;
        String telefone = edtTelefone.getText().toString().trim();

        if(!telefone.isEmpty()){
            if (telefone.length() == 11){
                if(valor >= 15){

                    progressBar.setVisibility(view.VISIBLE);

                    Toast.makeText(this,"Tudo certo!",Toast.LENGTH_SHORT).show();

                }else{
                    showDialog("Valor minimo para recarga é de R$ 15,00.");
                }
            }else{
                showDialog("Informe um número válido.");
            }
        }else{
            showDialog("Por favor, informe o número.");
        }

    }

    private void salvarRecarga(ExtratoModel extrato) {

        Deposito deposito = new Deposito();
        deposito.setId(extrato.getId());
        deposito.setValor(extrato.getValor());

        DatabaseReference depositoRef = FirebaseHelper.getDatabaseReference()
                .child("depositos")
                .child(deposito.getId());


        depositoRef.setValue(deposito).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                DatabaseReference updateDeposito = depositoRef
                        .child("data");
                updateDeposito.setValue(ServerValue.TIMESTAMP);

                usuario.setSaldo(usuario.getSaldo() + deposito.getValor());
                usuario.atualizarSaldo();

                Intent intent = new Intent(this, DepositoReciboActivity.class);
                intent.putExtra("idDeposito", deposito.getId());
                startActivity(intent);
                finish();

            }else{
                progressBar.setVisibility(View.GONE);
                showDialog("Não foi possível realizar o depósito, tente novamente mais tarde.");
            }
        });
    }

    private void salvarExtrato(double valorDeposito){

        ExtratoModel extrato =  new ExtratoModel();
        extrato.setOperacao("DEPOSITO");
        extrato.setValor(valorDeposito);
        extrato.setTipo("ENTRADA");

        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(FirebaseHelper.getIdFirebase())
                .child(extrato.getId());
        extratoRef.setValue(extrato).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                DatabaseReference updateExtrato = extratoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

                salvarRecarga(extrato);

            }else{
                showDialog("Não foi possível realizar o depósito, tente novamente mais tarde.");
            }
        });
    }

    private void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_info, null);

        TextView textTitulo = findViewById(R.id.textTitulo);
        textTitulo.setText("Atenção");

        TextView mensagem = findViewById(R.id.textMensagem);
        textTitulo.setText(msg);
        //textTitulo.setText();

        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> dialog.dismiss());

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes(){
        edtVal = findViewById(R.id.edtValRecarga);
        edtVal.setLocale(new Locale("PT", "br"));

        progressBar = findViewById(R.id.progressbar);
    }

    //Oculta o teclado do dispositivo
    private void ocultarTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtVal.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}