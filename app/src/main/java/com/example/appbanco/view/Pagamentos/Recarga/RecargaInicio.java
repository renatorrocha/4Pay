package com.example.appbanco.view.Pagamentos.Recarga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRecargaBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Recarga;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Home.Home;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.santalu.maskara.widget.MaskEditText;

import java.util.Locale;

public class RecargaInicio extends AppCompatActivity {

    private ActivityRecargaBinding binding;

    private CurrencyEditText edtVal;
    private AlertDialog dialog;
    private MaskEditText edtTelefone;
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

        //binding.edtTelefone.addTextChangedListener(CustomMask.Companion.mask("(##) #####-####", binding.edtTelefone, null));

        binding.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
            }
        });


        binding.ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));

        });

        iniciaComponentes();

    }

    public void validaDados(View view) {

        double valor = (double) edtVal.getRawValue() / 100;
        String numero = edtTelefone.getUnMasked();

        if (!numero.isEmpty()) {
            if (numero.length() == 11) {
                if (valor >= 15) {

                    progressBar.setVisibility(view.VISIBLE);

                    Recarga recarga = new Recarga();


                    salvarExtrato(valor, numero);

                } else {
                    showDialog("Valor minimo para recarga é de R$ 15,00.");
                }
            } else {
                showDialog("Informe um número válido.");
            }
        } else {
            showDialog("Por favor, informe o número.");
        }

    }

    private void salvarRecarga(ExtratoModel extrato, String numero) {

        Recarga recarga = new Recarga();
        recarga.setId(extrato.getId());
        recarga.setNumero(numero);
        recarga.setValor(extrato.getValor());


        DatabaseReference recargaRef = FirebaseHelper.getDatabaseReference()
                .child("recargas")
                .child(recarga.getId());


        recargaRef.setValue(recarga).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DatabaseReference updateRecarga = recargaRef
                        .child("data");
                updateRecarga.setValue(ServerValue.TIMESTAMP);

                Intent intent = new Intent(this, RecargaReciboActivity.class);
                intent.putExtra("idRecarga", recarga.getId());
                startActivity(intent);
                finish();

            } else {
                progressBar.setVisibility(View.GONE);
                showDialog("Não foi possível realizar a recarga, tente novamente mais tarde.");
            }
        });
    }

    private void salvarExtrato(double valor, String numero) {

        ExtratoModel extrato = new ExtratoModel();
        extrato.setOperacao("RECARGA");
        extrato.setValor(valor);
        extrato.setTipo("SAIDA");

        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(FirebaseHelper.getIdFirebase())
                .child(extrato.getId());
        extratoRef.setValue(extrato).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DatabaseReference updateExtrato = extratoRef
                        .child("data");
                updateExtrato.setValue(ServerValue.TIMESTAMP);

                salvarRecarga(extrato, numero);

            } else {
                showDialog("Não foi possível realizar o depósito, tente novamente mais tarde.");
            }
        });
    }

    private void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_info, null);

        TextView textTitulo = view.findViewById(R.id.textTitulo);
        textTitulo.setText("Atenção");

        TextView mensagem = view.findViewById(R.id.textMensagem);
        textTitulo.setText(msg);
        //textTitulo.setText();

        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> dialog.dismiss());

        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes() {
        edtVal = findViewById(R.id.edtValRecarga);
        edtVal.setLocale(new Locale("PT", "br"));
        edtTelefone = findViewById(R.id.edtTelefone);

        progressBar = findViewById(R.id.progressbar);
    }

    //Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtVal.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}