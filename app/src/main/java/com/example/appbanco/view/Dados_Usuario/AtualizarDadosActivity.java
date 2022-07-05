package com.example.appbanco.view.Dados_Usuario;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityAtualizarDadosBinding;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.example.appbanco.viewModel.SetUserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;
import com.thyagoneves.custom_mask_textwatcher.CustomMask;

import java.io.IOException;
import java.util.List;

public class AtualizarDadosActivity extends AppCompatActivity {

    private final int REQUEST_GALERIA = 100;

    private String caminhoImagem;

    private Usuario usuario;
    private Endereco endereco;
    private GetUserViewModel userViewModel;
    private SetUserViewModel setUserViewModel;
    private ActivityAtualizarDadosBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAtualizarDadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(GetUserViewModel.class);
        setUserViewModel = new ViewModelProvider(this).get(SetUserViewModel.class);

        userViewModel.verifyUserData();
        userViewModel.verifyEndereco();

        userViewModel.getUser.observe(this, sucess -> {
            if (sucess) {
                usuario = userViewModel.getUser();
            }
        });

        userViewModel.getEndereco.observe(this, sucess -> {
            if(sucess){
                endereco = userViewModel.getEndereco();
                configDados();

            }
        });

        configClicks();
    }

    public void validaDados(View view) {

        String nome = binding.edtNomeAtt.getText().toString();
        String email = binding.edtEmailAtt.getText().toString();
        String celular =  binding.edtNumeroAtt.getText().toString();
        String enderecoAtt =  binding.edtlogradouroAtt.getText().toString();


        if (!nome.isEmpty()) {
            if (!celular.isEmpty()) {
                if(!enderecoAtt.isEmpty()){



                ocultarTeclado();

                binding.progressBar.setVisibility(View.GONE);

                usuario.setNome(nome);
                usuario.setCelular(celular);
//                endereco.setLogradouro(enderecoAtt);

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
                    binding.edtlogradouroAtt.requestFocus();
                    binding.edtlogradouroAtt.setError("Informe seu Logradouro");
                }

            } else {
                binding.edtNumeroAtt.requestFocus();
                binding.edtNumeroAtt.setError("Informe seu número");
            }

        } else {
            binding.edtNomeAtt.requestFocus();
            binding.edtNomeAtt.setError("Informe seu nome");
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
        setUserViewModel.setUser(usuario);

        setUserViewModel.setUser.observe(this, sucess -> {
            if (sucess) {
                Toast.makeText(this, "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Não foi possível salvar as informações.", Toast.LENGTH_SHORT).show();

            }
        });
//        setUserViewModel.setEndereco(usuario.getId(), endereco);
//        setUserViewModel.setEndereco.observe(this, sucess -> {
//            if (sucess) {
//                Toast.makeText(this, "Endereco Salvo com sucesso.", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "Não foi possível salvar as informações.", Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    private void configDados() {

        binding.edtlogradouroAtt.setText(endereco.getLogradouro());
        binding.edtEmailAtt.setText(usuario.getEmail());
        binding.edtNomeAtt.setText(usuario.getNome());
        binding.edtNumeroAtt.addTextChangedListener(CustomMask.Companion.mask("(##) #####-####",
                binding.edtNumeroAtt, null));
        binding.edtNumeroAtt.setText(usuario.getCelular());

        if (usuario.getUrlImagem() != null) {
            Picasso.get().load(usuario.getUrlImagem())
                    .into(binding.ivUserFoto);
        }

        binding.progressBar.setVisibility((View.GONE));
    }

    private void configClicks() {
        binding.ivArrowBack.setOnClickListener(view -> {
            finish();
        });

        binding.buttonSalvar.setOnClickListener(view ->  {
            validaDados(view);
        });

        binding.ivUserFoto.setOnClickListener(v -> verificaPermissaogaleria());

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

    //Oculta o teclado do dispositivo
    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.edtNomeAtt.getWindowToken(),
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
                        binding.ivUserFoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),
                            imagemSelecionada);
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        binding.ivUserFoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}