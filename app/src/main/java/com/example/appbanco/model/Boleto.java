package com.example.appbanco.model;

import java.io.Serializable;

public class Boleto implements Serializable {
    private String para;
    private double valor;
    private long data;
    private String codigo;
    private String desc;

    public Boleto(String para, double valor, String codigo, String desc) {
        this.para = para;
        this.valor = valor;
        this.codigo = codigo;
        this.desc = desc;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
