package com.example.fruit_application.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityOrderPlacedBinding;

public class OrderPlacedActivity extends AppCompatActivity {
    ActivityOrderPlacedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order_placed);
        binding.buttonBacktoStore.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(() -> {
            binding.buttonBacktoStore.setVisibility(View.VISIBLE);
        }, 1000);
        binding.buttonBacktoStore.setOnClickListener(view->{
            finish();
        });
    }
}