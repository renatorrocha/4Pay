package com.example.appbanco.model;

public class ListaSeguro {

    private String tituloSeguro;
    private String descSeguro;
    private String segExpira;

    public ListaSeguro(String tituloSeguro, String descSeguro, String segExpira) {
        this.tituloSeguro = tituloSeguro;
        this.descSeguro = descSeguro;
    }

    public String getTituloSeguro() {
        return tituloSeguro;
    }

    public void setTituloSeguro(String tituloSeguro) {
        this.tituloSeguro = tituloSeguro;
    }

    public String getDescSeguro() {
        return descSeguro;
    }

    public void setDescSeguro(String descSeguro) {
        this.descSeguro = descSeguro;
    }

    public String getSegExpira() {
        return segExpira;
    }

    public void setSegExpira(String segExpira) {
        this.segExpira = segExpira;
    }
}
