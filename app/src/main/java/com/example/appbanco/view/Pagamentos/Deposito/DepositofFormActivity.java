package com.example.appbanco.view.Pagamentos.Deposito;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.appbanco.R;

import java.util.Locale;

public class DepositofFormActivity extends AppCompatActivity {

    private CurrencyEditText edtValor;
    private AlertDialog dialog;
    private Button btnConfirmar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito_form);

        iniciaComponentes();

        btnConfirmar = findViewById(R.id.btnConfirmar);

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

            //Salvar Extrato

        }else{

            showDialog();
        }
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_info, null);

        TextView textTitulo = view.findViewById(R.id.textTitulo);
        textTitulo.setText("Atenção");

        TextView mensagem = view.findViewById(R.id.textMensagem);
        textTitulo.setText("Digite um valor maior que 0.");

        Button btnOk = view.findViewById(R.id.btnOk);
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