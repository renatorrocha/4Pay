package com.example.appbanco.model;

import java.io.Serializable;
import java.util.Random;

public class Cartao implements Serializable {
    private String tipo;
    private String idDono;
    private String nome;
    private String senha;
    private String numeros;
    private double limite;
    private double saldo;
    private double pin;
    private long dataVencimento;
    private long dataCricacao;

    public Cartao() {
    }

    public void gerarCodigoCartao() {
        String numeros = "";
        int verify = 0;

        for (int i = 0; i < 100; i++) {
            int gerados = (int) Math.round(Math.random() * 10000);
            if (gerados > 1000) {
                numeros = numeros + " " + gerados;
                verify++;
                if (verify == 4) {
                    break;
                }
            }
        }

        this.numeros = numeros;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdDono() {
        return idDono;
    }

    public void setIdDono(String idDono) {
        this.idDono = idDono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getPin() {
        return pin;
    }

    public void setPin(double pin) {
        this.pin = pin;
    }

    public long getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(long dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public long getDataCricacao() {
        return dataCricacao;
    }

    public void setDataCricacao(long dataCricacao) {
        this.dataCricacao = dataCricacao;
    }
}
