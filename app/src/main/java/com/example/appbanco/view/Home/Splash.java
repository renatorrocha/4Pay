package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.appbanco.R;
import com.example.appbanco.help.FirebaseHelper;
import com.example.appbanco.view.Login_Cadastro.Login;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::verificarLogin, 3000);

        verificarLogin();
    }

    private void verificarLogin() {
        if (FirebaseHelper.getAutenticado()) {
            startActivity(new Intent(this, Home.class));
        } else {
            startActivity(new Intent(this, Login.class));
        }
    }
}