package com.example.appbanco.view.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.appbanco.R;
import com.example.appbanco.databinding.ActivityHomeBinding;
import com.example.appbanco.view.Home.HomeFragments.ConfigsFragment;
import com.example.appbanco.view.Home.HomeFragments.ExtratoFragment;
import com.example.appbanco.view.Home.HomeFragments.HomeFragment;
import com.example.appbanco.view.Home.HomeFragments.ProfileFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Home extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private MeowBottomNavigation bnv_Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bnv_Main = findViewById(R.id.bnv_Main);
        bnv_Main.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bnv_Main.add(new MeowBottomNavigation.Model(2,R.drawable.ic_person));
        bnv_Main.add(new MeowBottomNavigation.Model(3,R.drawable.ic_dados));
        bnv_Main.add(new MeowBottomNavigation.Model(4,R.drawable.ic_conf));

        bnv_Main.show(1,true);
        replace(new HomeFragment());
        bnv_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId())
                {
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new ProfileFragment());
                        break;

                    case 3:
                        replace(new ExtratoFragment());
                        break;

                    case 4:
                        replace(new ConfigsFragment());
                        break;
                }

                return null;
            }
        });

    }

    private void replace(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}