package com.example.appbanco.view.Pagamentos.Pix.Chave_Pix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityRegistrarChavePixBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.ChavePix;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.Pagamentos.Cartoes.CartaoCriado;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrarChavePix extends AppCompatActivity {

    ActivityRegistrarChavePixBinding binding;
    private Usuario usuario;
    private List<ChavePix> listChaves = new ArrayList<>();
    List<String> usersList = new ArrayList<>();
    private String tipoChave = "";
    private Boolean verify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarChavePixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tipoChave = getIntent().getStringExtra("tipoChave");
        getUserData();
        getAllUsuarios();


        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnCriarChave.setOnClickListener(view1 -> {
            validarDados(tipoChave);
        });
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);

                setData();
                getUserChavePix();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserChavePix() {
        DatabaseReference chavesPixRef = FirebaseHelper.getDatabaseReference()
                .child("chavesPix")
                .child(FirebaseHelper.getIdFirebase());
        chavesPixRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listChaves.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChavePix chavePixItem = ds.getValue(ChavePix.class);
                    listChaves.add(chavePixItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setData() {

        String setTipoChave = tipoChave.substring(0, 1).toUpperCase() + tipoChave.substring(1);

        binding.tvTitulo.setText(getString(R.string.txt_registrar_chave, setTipoChave));
        binding.btnCriarChave.setText(getString(R.string.txt_registrar_chave, tipoChave));
        binding.tvDesc.setText(getString(R.string.txt_desc_tipo_chave, setTipoChave));
        binding.tvChave.setText(setTipoChave);

        if (tipoChave.equals("email")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_email);
            binding.tvChaveUsuario.setText(usuario.getEmail());

        } else if (tipoChave.equals("celular")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_cel);
            binding.tvChaveUsuario.setText(usuario.getCelular());

        } else if (tipoChave.equals("cpf")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_id);
            binding.tvChaveUsuario.setText(usuario.getCpf());


        } else if (tipoChave.equals("chaveAleatoria")) {
            binding.ivTipoChave.setImageResource(R.drawable.ic_sec);
            binding.tvChaveUsuario.setText("sadlkjçasd218973459");
        }

    }

    private void validarDados(String tipoChave) {
        Boolean verificarChaveExistente = false;

        if (!tipoChave.equals("")) {
            if (listChaves.size() > 0) {

                for (int i = 0; i < listChaves.size(); i++) {
                    if (listChaves.get(i).getTipoChave().equals(tipoChave)) {
                        verificarChaveExistente = true;
                    }
                }

                if (!verificarChaveExistente) {
                    criarChavePix(tipoChave);
                } else {
                    Toast.makeText(this, "Você ja possui este tipo de chave.", Toast.LENGTH_SHORT).show();
                }


            } else {
                criarChavePix(tipoChave);
            }

        }

    }

    private void criarChavePix(String tipoChave) {

        ChavePix chavePix = new ChavePix();

        if (!tipoChave.equals("chaveAletoria")) {

            chavePix.setTipoChave(tipoChave);
            chavePix.setLimite(usuario.getRendimento());

            if (tipoChave.equals("email")) {
                chavePix.setChave(usuario.getEmail());
            }
            if (tipoChave.equals("celular")) {
                chavePix.setChave(usuario.getCelular());
            }

            if (tipoChave.equals("cpf")) {
                chavePix.setChave(usuario.getCpf());
            }

            getAllChavesPix(chavePix);


        } else {
            chavePix.criarChaveAleatoria();
            chavePix.setLimite(usuario.getRendimento());
        }

    }

    private void setChavePix(ChavePix chavePix) {

        DatabaseReference chavePixRef = FirebaseHelper.getDatabaseReference()
                .child("chavesPix")
                .child(FirebaseHelper.getIdFirebase())
                .child(chavePix.getTipoChave());

        chavePixRef.setValue(chavePix).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Chave Pix criada.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ChaveCriada.class);
                intent.putExtra("tipoChave", chavePix.getTipoChave());
                startActivity(intent);


            } else {
                Toast.makeText(this, "Não foi possivel criar a chave.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllUsuarios() {

        DatabaseReference allUsersRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios");
        allUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuarios = ds.getValue(Usuario.class);
                    usersList.add(usuarios.getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAllChavesPix(ChavePix chavePix) {

        for (int i = 0; i < usersList.size(); i++) {

            DatabaseReference chavesPixRef = FirebaseHelper.getDatabaseReference()
                    .child("chavesPix")
                    .child(usersList.get(i));
            int finalI = i;
            chavesPixRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ChavePix chavePixAll = ds.getValue(ChavePix.class);

                        if (chavePix.getChave().equals(chavePixAll.getChave())) {
                            verify = true;
                        }
                    }

                    if(finalI == usersList.size() - 1 ){
                        if (!verify) {
                            setChavePix(chavePix);
                        } else {
                            Toast.makeText(RegistrarChavePix.this, "Esta chave ja está cadastrada por um usuário.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }




    }
}