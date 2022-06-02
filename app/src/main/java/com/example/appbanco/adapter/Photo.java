package com.example.appbanco.adapter;

public class Photo {

    private int foto;
    private String titulo;

    public Photo(int foto, String titulo) {
        this.foto = foto;
        this.titulo = titulo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
