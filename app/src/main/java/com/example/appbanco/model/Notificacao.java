package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class Notificacao {

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
