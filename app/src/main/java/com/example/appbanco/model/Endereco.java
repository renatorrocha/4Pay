package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class Endereco {
    private String id;
    private String cel;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String numero;
    private String localidade;
    private String uf;

    public Endereco() {
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference();
        this.setId(enderecoRef.push().getKey());
    }

    public void salvar() {
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.id);
        enderecoRef.setValue(this);
    }

    //public void delete() {
    // DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
    //    .child("enderecos")
    // .child(FirebaseHelper.getIdFirebase())
    //.child(this.id);
    //enderecoRef.removeValue();
    //}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}


