package com.example.fruit_application.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fruit_application.R;
import com.example.fruit_application.databinding.ActivityAddCardBinding;

public class AddCardActivity extends AppCompatActivity {
    ActivityAddCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_card);
        binding.imageViewBack.setOnClickListener(view ->{
            onBackPressed();
        });
    }
}