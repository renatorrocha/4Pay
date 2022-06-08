package com.example.appbanco.model;

import com.example.appbanco.help.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Transferencia implements Serializable {

    private String id;
    private String idUserOrigem;
    private String idUserDestino;
    private Date data;
    private double valor;

    public Transferencia(){
        DatabaseReference transfRef = FirebaseHelper.getDatabaseReference();
        setId(transfRef.push().getKey());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUserOrigem() {
        return idUserOrigem;
    }

    public void setIdUserOrigem(String idUserOrigem) {
        this.idUserOrigem = idUserOrigem;
    }

    public String getIdUserDestino() {
        return idUserDestino;
    }

    public void setIdUserDestino(String idUserDestino) {
        this.idUserDestino = idUserDestino;
    }

    public Date getData() {return data;}

    public void setData(Date data) {this.data = data;}

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
