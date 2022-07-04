package com.example.appbanco.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GetUserViewModel extends ViewModel {

    private MutableLiveData<Usuario> mGetuser = new MutableLiveData<>();
    public LiveData<Usuario> getUser = mGetuser;
    private Usuario usuario;


    public void verifyAutenticado(){

        if(FirebaseHelper.getAutenticado()){
            getUserData();

        }
    }

    public void getUserData() {

        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                mGetuser.setValue(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Usuario getUser(){
        return this.usuario;
    }

}
