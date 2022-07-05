package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class Notificacao implements Serializable {

    private String id;
    private String idCobrador;
    private String idDestinario;
    private String idOperacao;
    private long data;
    private String operação;
    private boolean lida = false;

    public Notificacao() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference();
        setId(notiRef.push().getKey());
    }

    public void enviar() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(getIdDestinario())
                .child(getId());
        notiRef.setValue(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DatabaseReference notiUpdate = notiRef
                        .child("data");
                notiUpdate.setValue(ServerValue.TIMESTAMP);
            }
        });
    }

    public void salvar() {
        DatabaseReference notiRef = FirebaseHelper.getDatabaseReference()
                .child("notificacoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.id)
                .child("lida");
        notiRef.setValue(!this.lida);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCobrador() {
        return idCobrador;
    }

    public void setIdCobrador(String idCobrador) {
        this.idCobrador = idCobrador;
    }

    public String getIdDestinario() {
        return idDestinario;
    }

    public void setIdDestinario(String idDestinario) {
        this.idDestinario = idDestinario;
    }

    public String getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(String idOperacao) {
        this.idOperacao = idOperacao;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getOperação() {
        return operação;
    }

    public void setOperação(String operação) {
        this.operação = operação;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
