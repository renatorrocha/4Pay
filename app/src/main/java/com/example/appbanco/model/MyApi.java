package com.example.appbanco.model;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApi {


    @GET("{cep}/json/")
    Call<Endereco> getData(@Path("cep") String cep);
}


