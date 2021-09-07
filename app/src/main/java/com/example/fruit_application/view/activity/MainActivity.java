package com.example.fruit_application.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityMainBinding;
import com.example.fruit_application.view.fragment.AddCartFragment;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    NavController navController;
    public String idUser;
    public FragmentManager manager;
    AddCartFragment addCartFragment;
    private boolean checkFloatingButton = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(mainBinding.bottomNavigationView, navController);
        manager = getSupportFragmentManager();
        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");
        mainBinding.fab.setOnClickListener(view ->{
            if (!checkFloatingButton){
                initFloatingButtonFragment();
                checkFloatingButton=true;
            } else {
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.remove(addCartFragment);
                transaction.commit();
                checkFloatingButton=false;
            }
        });
    }
    public void goToMoreFruitActivity(int i){
        Intent intent = new Intent(MainActivity.this, ShowMoreFruitActivity.class);
        intent.putExtra("type",i);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
    public void backToLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void initFloatingButtonFragment(){
        addCartFragment = new AddCartFragment();
        FragmentTransaction transaction= manager.beginTransaction();
        if(addCartFragment == null){
            addCartFragment = new AddCartFragment();
        }
        transaction.add(R.id.nav_host_fragment, addCartFragment, "Floating");
        transaction.addToBackStack("Floating");
        transaction.commit();
    }
}