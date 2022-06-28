package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExtratoModel implements Serializable {

    private String id;
    private String operacao;
    private String tituloExtrato;
    private String pessoa;
    private double valor;
    private long data;
    private String tipo;

    public ExtratoModel() {
        DatabaseReference extratoRef = FirebaseHelper.getDatabaseReference();
        setId(extratoRef.push().getKey());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getTituloExtrato() {
        return tituloExtrato;
    }

    public void setTituloExtrato(String tituloExtrato) {
        this.tituloExtrato = tituloExtrato;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}



