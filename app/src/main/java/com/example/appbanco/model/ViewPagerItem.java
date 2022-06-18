package com.example.appbanco.model;

public class ViewPagerItem {

    int imgID;
    String titulo, descricao;

    public ViewPagerItem(int imgID, String titulo, String descricao) {
        this.imgID = imgID;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
