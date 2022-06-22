package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityTipoChaveBinding;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.view.Pagamentos.Pix.Pix;

public class TipoChave extends AppCompatActivity {

    private ActivityTipoChaveBinding binding;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipoChaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCriarChave.setOnClickListener(view -> {
            showDialogStatus();
        });

        binding.ivArrowBack.setOnClickListener(view -> {
            startActivity(new Intent(this, Pix.class));
        });
    }


    private void showDialogStatus() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog
        );

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_chavepix, null);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        ConstraintLayout clEmail = view.findViewById(R.id.clEmail);
        ConstraintLayout clCelular = view.findViewById(R.id.clCelular);
        ConstraintLayout clCpf = view.findViewById(R.id.clCpf);
        ConstraintLayout clChaveAleatoria = view.findViewById(R.id.clEmail);

        ivClose.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        clEmail.setOnClickListener(view1 -> {
            Intent intent = new Intent(TipoChave.this, RegistrarChavePix.class);
            intent.putExtra("tipoChave", "email");
            startActivity(intent);
        });

        clCelular.setOnClickListener(view1 -> {
            Intent intent = new Intent(TipoChave.this, RegistrarChavePix.class);
            intent.putExtra("tipoChave", "celular");
            startActivity(intent);
        });

        clCpf.setOnClickListener(view1 -> {
            Intent intent = new Intent(TipoChave.this, RegistrarChavePix.class);
            intent.putExtra("tipoChave", "cpf");
            startActivity(intent);
        });

        clChaveAleatoria.setOnClickListener(view1 -> {
            Intent intent = new Intent(TipoChave.this, RegistrarChavePix.class);
            intent.putExtra("tipoChave", "chaveAleatoria");
            startActivity(intent);
        });



        builder.setView(view);

        dialog = builder.create();
        dialog.show();
    }
}