package com.example.appbanco.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Cartao implements Serializable {
    private String tipo;
    private String idDono;
    private String nome;
    private String senha;
    private String numeros;
    private int limite;
    private String pin;
    private double saldo;
    private String dataVencimento;
    private String dataCriacao;

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

    public void gerarPin(){
        String pin = "";

        for (int i = 0; i < 10; i++) {
            int gerados = (int) Math.round(Math.random() * 1000);
            if (gerados > 100) {
                pin = Integer.toString(gerados);
                break;
            }
        }

        this.setPin(pin);
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

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
