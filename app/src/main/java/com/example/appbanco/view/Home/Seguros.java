package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appbanco.R;
import com.example.appbanco.adapter.VPAdapter;
import com.example.appbanco.model.ViewPagerItem;
import com.example.appbanco.view.Seguros.SegurosAtivos;

import java.util.ArrayList;

public class Seguros extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    private ImageView ivArrowBack;
    private Button btnSeusSeguros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguros);

        ivArrowBack = findViewById(R.id.ivArrowBack);
        viewPager2 = findViewById(R.id.viewPager);
        int[] images = {R.drawable.cartao_credito, R.drawable.seguro_de_vida};
        String[] titulo = {"Seguro De Cartão", "Seguro De Vida"};
        String[] desc = {getString(R.string.txt_seguro_cartao), getString(R.string.txt_seguro_vida)};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++){
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i],titulo[i],desc[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        btnSeusSeguros = findViewById(R.id.btnProximo);

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);

        viewPager2.setAdapter(vpAdapter);

        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);

        viewPager2.setOffscreenPageLimit(2);

        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        btnSeusSeguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Seguros.this, SegurosAtivos.class));
            }
        });

        ivArrowBack.setOnClickListener(view1 -> {
            startActivity(new Intent(this, Home.class));
        });

    }
}