package com.example.appbanco.model;


import com.example.appbanco.help.FirebaseHelper;


import java.util.UUID;

public class ChavePix {
    private String idUsuario;
    private String tipoChave;
    private String chave;
    private String limite;
    private boolean status;

    public ChavePix() {
      this.setIdUsuario(FirebaseHelper.getIdFirebase());
      this.setStatus(true);

    }

    public void criarChaveAleatoria(){
        this.setChave( UUID.randomUUID().toString());
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
