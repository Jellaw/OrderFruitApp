package com.example.fruit_application.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityOrderplaceBinding;

public class OderPlacedActivity extends AppCompatActivity {
    ActivityOrderplaceBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderplace);
        new Handler().postDelayed(() -> {
            binding.buttonBacktoStore.setVisibility(View.VISIBLE);
        }, 1000);
        binding.buttonBacktoStore.setOnClickListener(view -> {
            finish();
        });
    }
}
