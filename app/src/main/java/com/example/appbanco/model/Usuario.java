package com.example.appbanco.model;

import com.google.firebase.database.Exclude;

public class Usuario {

        private String id;
        private String nome;
        private String cpf;
        private String dtanascimento;
        private String email;
        private String urlImagem;
        private double saldo;
        private String senha;

        public Usuario() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getDtanascimento() {
            return dtanascimento;
        }

        public void setDtanascimento(String dtanascimento) {
            this.dtanascimento = dtanascimento;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrlImagem() {
            return urlImagem;
        }

        public void setUrlImagem(String urlImagem) {
            this.urlImagem = urlImagem;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        @Exclude
        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
