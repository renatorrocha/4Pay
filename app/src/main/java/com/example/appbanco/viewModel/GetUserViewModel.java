package com.example.appbanco.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.Endereco;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetUserViewModel extends ViewModel {

    private MutableLiveData<Boolean> mGetUser = new MutableLiveData<>();
    public LiveData<Boolean> getUser = mGetUser;

    private MutableLiveData<Boolean> mGetNoti = new MutableLiveData<>();
    public LiveData<Boolean> getNoti = mGetNoti;

    private MutableLiveData<Boolean> mGetCartao = new MutableLiveData<>();
    public LiveData<Boolean> getCartao = mGetCartao;

    private MutableLiveData<Boolean> mGetExtrato = new MutableLiveData<>();
    public LiveData<Boolean> getExtrato = mGetCartao;

    private MutableLiveData<Boolean> mGetEndereco = new MutableLiveData<>();
    public LiveData<Boolean> getEndereco = mGetEndereco;

    private Usuario usuario;
    private Endereco endereco;
    private List<Notificacao> notiList = new ArrayList<>();
    private List<Cartao> cartoesList = new ArrayList<>();
    private List<ExtratoModel> extratoList = new ArrayList<>();


    public void verifyUserData() {
        if (FirebaseHelper.getAutenticado()) {
            getUserData();
        }
    }

    public void verifyNoti() {
        if (FirebaseHelper.getAutenticado()) {
            getAllNoti();
        }
    }

    public void verifyCartao() {
        if (FirebaseHelper.getAutenticado()) {
            getAllCartoes();
        }
    }

    public void verifyExtrato() {
        if (FirebaseHelper.getAutenticado()) {
            getAllExtratos();
        }
    }

    public void verifyEndereco() {
        if (FirebaseHelper.getAutenticado()) {
            getAllEndereco();
        }
    }

    private void getUserData() {
        DatabaseReference userRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                mGetUser.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Usuario getUser() {
        return this.usuario;
    }

    private void getAllNoti() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(FirebaseHelper.getIdFirebase());
        notiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Notificacao noti = ds.getValue(Notificacao.class);
                        notiList.add(noti);
                    }

                    mGetNoti.setValue(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Notificacao> getNoti() {
        return notiList;
    }

    private void getAllCartoes() {
        DatabaseReference cartoesRef = FirebaseHelper.getDatabaseReference()
                .child("cartoes")
                .child(FirebaseHelper.getIdFirebase());
        cartoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Cartao cartao = ds.getValue(Cartao.class);
                        cartoesList.add(cartao);
                    }
                    mGetCartao.setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Cartao> getCartao() {
        return cartoesList;
    }

    private void getAllExtratos() {
        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference()
                .child("extratos")
                .child(FirebaseHelper.getIdFirebase());
        extratoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    extratoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ExtratoModel extrato = ds.getValue(ExtratoModel.class);
                        extratoList.add(extrato);
                    }
                    mGetExtrato.setValue(true);
                    Collections.reverse(extratoList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<ExtratoModel> getExtrato() {
        return extratoList;
    }

    private void getAllEndereco(){
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        endereco = dataSnapshot.getValue(Endereco.class);
                    }
                    mGetEndereco.setValue(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public Endereco getEndereco(){
        return endereco;
    }
}
