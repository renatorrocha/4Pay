package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ChavesPixAdapter;
import com.example.appbanco.databinding.ActivityTipoChaveBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.ChavePix;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TipoChave extends AppCompatActivity implements ChavesPixAdapter.OnLongClick {

    private List<ChavePix> chavesPixList = new ArrayList<>();
    private ActivityTipoChaveBinding binding;
    private AlertDialog dialog;
    private ChavesPixAdapter chavesPixAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTipoChaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserChavePix();

        binding.btnCriarChave.setOnClickListener(view -> {
            showDialogStatus();
        });

        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.rvChavesPix.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChavesPix.setHasFixedSize(true);
        chavesPixAdapter = new ChavesPixAdapter(chavesPixList, getBaseContext(), this);
        binding.rvChavesPix.setAdapter(chavesPixAdapter);

    }

    private void getUserChavePix() {
        DatabaseReference chavesPixRef = FirebaseHelper.getDatabaseReference()
                .child("chavesPix")
                .child(FirebaseHelper.getIdFirebase());
        chavesPixRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chavesPixList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChavePix chavePixItem = ds.getValue(ChavePix.class);
                    chavesPixList.add(chavePixItem);
                }
                chavesPixAdapter.notifyDataSetChanged();
                binding.tvQuantdChaves.setText(getString(R.string.txt_chave_quantidade, Integer.toString(chavesPixList.size())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
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
        ConstraintLayout clChaveAleatoria = view.findViewById(R.id.clChaveAleatoria);

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

    @Override
    public void OnLongClickListener(ChavePix chavePix) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Text", chavePix.getChave());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this, "Chave copiada", Toast.LENGTH_SHORT).show();
    }
}