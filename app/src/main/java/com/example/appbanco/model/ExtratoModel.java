package com.example.appbanco.model;

import java.util.ArrayList;
import java.util.List;

public class ExtratoModel {

    private String tituloExtrato;
    private String pessoa;
    private String valor;
    private String data;

    public ExtratoModel(String tituloExtrato, String pessoa, String valor, String data){
        this.tituloExtrato = tituloExtrato;
        this.pessoa = pessoa;
        this.valor = valor;
        this.data = data;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}



