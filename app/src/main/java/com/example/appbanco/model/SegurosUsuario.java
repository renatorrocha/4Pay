package com.example.appbanco.model;

public class SegurosUsuario {

    private boolean seguroVida;
    private boolean seguroCartao;


    public SegurosUsuario() {
        this.seguroVida = true;
        this.seguroCartao = true;
    }

    public boolean getSeguroVida() {
        return seguroVida;
    }

    public void setSeguroVida(boolean seguroVida) {
        this.seguroVida = seguroVida;
    }

    public boolean getSeguroCartao() {
        return seguroCartao;
    }

    public void setSeguroCartao(boolean seguroCartao) {
        this.seguroCartao = seguroCartao;
    }
}
