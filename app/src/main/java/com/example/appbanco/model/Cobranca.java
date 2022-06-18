package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Cobranca implements Serializable {

    private String id;
    private String idCobrador;
    private String idDestinatario;
    private double valor;
    private long data;
    private boolean paga = false;

    public Cobranca() {
        DatabaseReference cobrarRef = FirebaseHelper.getDatabaseReference();
        setId(cobrarRef.push().getKey());
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

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }
}
