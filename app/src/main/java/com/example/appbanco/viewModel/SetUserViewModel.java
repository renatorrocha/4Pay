package com.example.appbanco.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DatabaseReference;

public class SetUserViewModel extends ViewModel {

    private MutableLiveData<Boolean> mSetUser = new MutableLiveData<>();
    public LiveData<Boolean> setUser = mSetUser;

    private MutableLiveData<Boolean> mSetEndereco = new MutableLiveData<>();
    public LiveData<Boolean> setEndereco = mSetEndereco;


    public void setUser(Usuario usuario) {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(usuario.getId());
        usuarioRef.setValue(usuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mSetUser.setValue(true);
            } else {
                mSetUser.setValue(false);

            }
        });
    }

    public void setEndereco(String idUsuario, Endereco endereco) {
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(idUsuario)
                .child(endereco.getId());
        enderecoRef.setValue(endereco).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mSetEndereco.setValue(true);
            } else {
                mSetEndereco.setValue(false);

            }
        });
    }
}
