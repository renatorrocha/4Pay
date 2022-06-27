package com.example.appbanco.view.Dados_Usuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.List;

public class AtualizarDadosActivity extends AppCompatActivity {

    private final int REQUEST_GALERIA = 100;

    private ImageView ivArrowBack;
    private EditText edtNomeAtt;
    private EditText edtNumeroAtt;
    private EditText edtEmailAtt;
    private EditText edtLogradouroAtt;
    private ProgressBar progressBar;
    private Usuario usuario;
    private Button buttonSalvar;
    private ImageView ivUserFoto;

    private String caminhoImagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);

        iniciaComponetes();
        getUserData();
        configClicks();
    }

    public void validaDados(View view) {

        String nome = edtNomeAtt.getText().toString();
        String email = edtEmailAtt.getText().toString();
        String celular = edtNumeroAtt.getText().toString();
        String endereco = edtLogradouroAtt.getText().toString();


        if (!nome.isEmpty()) {
            if (!celular.isEmpty()) {

                ocultarTeclado();

                progressBar.setVisibility(View.GONE);

                usuario.setNome(nome);
                usuario.setCelular(celular);

                if (caminhoImagem != null) {
                    salvarImagemFirebase();
                } else {
                    salvarDadosUser();
                }

                /*if(!endereco.isEmpty()){


                }else{
                    edtLogradouroAtt.requestFocus();
                    edtLogradouroAtt.setError("Informe seu endereço");
                }*/
            } else {
                edtNumeroAtt.requestFocus();
                edtNumeroAtt.setError("Informe seu número");
            }

        } else {
            edtNomeAtt.requestFocus();
            edtNomeAtt.setError("Informe seu nome");
        }

    }

    private void salvarImagemFirebase() {

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("perfil")
                .child(FirebaseHelper.getIdFirebase() + ".JPEG");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                        .addOnCompleteListener(task -> {

                            usuario.setUrlImagem(task.getResult().toString());
                            salvarDadosUser();

                        }))
                .addOnFailureListener(e -> Toast
                        .makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void salvarDadosUser() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Informações salvas com sucesso.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Não foi possível salvar as informações.",
                        Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });

    }

    private void configDados(Usuario usuario) {
        edtEmailAtt.setText(usuario.getEmail());
        edtNomeAtt.setText(usuario.getNome());
        edtNumeroAtt.setText(usuario.getCelular());
        //edtLogradouroAtt.setText(usuario.getEndereco().getLogradouro());

        if (usuario.getUrlImagem() != null) {
            Picasso.get().load(usuario.getUrlImagem())
                    .into(ivUserFoto);
        }

        progressBar.setVisibility((View.GONE));
    }

    private void configClicks() {
        ivArrowBack.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       startActivity(new Intent(AtualizarDadosActivity.this,
                                                               MeusDadosActivity.class));
                                                   }
                                               });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados(view);
            }
        });

        ivUserFoto.setOnClickListener(v -> verificaPermissaogaleria());

    }

    private void verificaPermissaogaleria() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }


            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AtualizarDadosActivity.this, "Permissão negada", Toast.LENGTH_SHORT);
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permissão negada")
                .setDeniedMessage("Você precisa aceitar a permissão para acessar a galeria do " +
                        "dispositivo, deseja fazer isso agora?")
                .setDeniedCloseButtonText("não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

    }

    //Método pra abrir a galeria do dispositivo
    private void abrirGaleria() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }


    //Metodo pra resgatar os dados do usuario
    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                configDados(usuario);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Metodo pra iniciar o FindView dos componentes
    private void iniciaComponetes() {

        ivArrowBack = findViewById(R.id.ivArrowBack);
        edtNomeAtt = findViewById(R.id.edtNomeAtt);
        edtNumeroAtt = findViewById(R.id.edtNumeroAtt);
        edtEmailAtt = findViewById(R.id.edtEmailAtt);
        progressBar = findViewById(R.id.progressBar);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        edtLogradouroAtt = findViewById(R.id.edtlogradouroAtt);
        ivUserFoto = findViewById(R.id.ivUserFoto);

    }

    //Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtNomeAtt.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALERIA) {

                Bitmap bitmap;

                Uri imagemSelecionada = data.getData();
                caminhoImagem = data.getData().toString();

                if (Build.VERSION.SDK_INT < 28) {

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                imagemSelecionada);
                        ivUserFoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),
                            imagemSelecionada);
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        ivUserFoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}