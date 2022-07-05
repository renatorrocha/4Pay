package com.example.appbanco.model;

public class SeguroModel {
    private String tipo;
    private double valor;
    private String desc;
    private boolean status;
    private String dataVencimento;

    public SeguroModel() {

    }

    public SeguroModel(String tipo, double valor, String desc, boolean status, String dataVencimento) {
        this.tipo = tipo;
        this.valor = valor;
        this.desc = desc;
        this.status = status;
        this.dataVencimento = dataVencimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
