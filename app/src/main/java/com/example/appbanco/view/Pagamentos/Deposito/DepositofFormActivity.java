package com.example.appbanco.view.Pagamentos.Deposito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Deposito;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DepositofFormActivity extends AppCompatActivity {

    private CurrencyEditText edtValor;
    private AlertDialog dialog;
    private Button btnConfirmar;
    private ImageView ivArrowBack;
    private ProgressBar progressBar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito_form);

        recuperaUsuario();

        iniciaComponentes();

        btnConfirmar = findViewById(R.id.btnConfirmar);
        ivArrowBack = findViewById(R.id.ivArrowBack);

        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDeposito();
            }
        });
    }

    public void validaDeposito(){
        double valorDeposito = (double) edtValor.getRawValue() / 100;

        if(valorDeposito > 0){

            ocultarTeclado();

            progressBar.setVisibility(View.VISIBLE);

            salvarExtrato(valorDeposito);

        }else{

            showDialog("Digite um valor maior que 0.");
        }
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

                salvarDeposito(extrato);
                
            }else{
                showDialog("Não foi possível realizar o depósito, tente novamente mais tarde.");
            }
        });
    }

    private void salvarDeposito(ExtratoModel extrato) {

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

                Intent intent = new Intent(this,DepositoReciboActivity.class);
                intent.putExtra("idDeposito", deposito.getId());
                startActivity(intent);
                finish();

            }else{
                progressBar.setVisibility(View.GONE);
                showDialog("Não foi possível realizar o depósito, tente novamente mais tarde.");
            }
        });
    }

    private void recuperaUsuario(){
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        edtValor = findViewById(R.id.edtValDeposito);
        edtValor.setLocale(new Locale("PT", "br"));

        progressBar = findViewById(R.id.progressbar);
    }

    //Oculta o teclado do dispositivo
    private void ocultarTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtValor.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}